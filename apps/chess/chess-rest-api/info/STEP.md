### Step Processing Architecture (package `pl.derleta.chess.step`)

This document describes the general idea and dependencies in the game save processing steps module. It contains
explanation of roles of Handlers, Processors and Providers and their relationships, without going into details of
individual classes.

#### Module Purpose

The module is responsible for executing subsequent steps of the game save process (e.g. creating a season, starting a
month, closing stages etc.) in an extensible and configurable way. The core solution consists of:

- Step Handlers (step execution coordination),
- Processors (atomic activities performed within a step),
- Providers (input data providers for processors),
- Shared contexts and registries (binding everything into a coherent pipeline).

---

### Roles and Responsibilities

#### Handlers (GameSaveStepHandler)

- Responsible for handling specific process "step", e.g. "START_MONTH"
- Each handler:
    - defines which step it handles (`getStep()`),
    - declares list of required Providers (data provider classes),
    - builds `ProcessingContext` based on `GameSaveRequest` and data from Providers (via `StepDataCollector`),
    - gets list of Processors for given step from registry and runs them in defined order,
    - saves game/stage state after executing steps.
- Handlers are mapped to `NextStep` values through configuration (`GameSaveHandlerConfig`), which allows easily finding
  proper handler based on current step.

#### Processors (StepProcessor)

- Implement single, coherent activity performed within step (e.g. data preparation, model updates, calculations).
- Each processor:
    - operates on `ProcessingContext`,
    - can retrieve data from context (Provider results),
    - can set `processFlags` (control further flow within step),
    - can write `results` (partial results for later persistence),
    - defines `getOrder()` determining execution order relative to other processors of same step.
- Processor binding with step type is configured with `@StepProcessorFor(StepType)` annotation. Registration to
  `StepProcessorRegistry` happens automatically in `StepProcessorConfig` (scan of all `StepProcessor` beans).

#### Providers (DataProvider)

- Domain data sources for processors, parameterized with game save ID (`UUID gameSaveId`) and optional additional
  arguments.
- Return data in unified wrapper `StepDataResult<? extends ModelData>`, which allows handling both single objects and
  data lists.
- Don't execute processing logic - only provide data necessary for processing.

#### Data Collector (StepDataCollector)

- Based on declared Providers list and request context calls appropriate Provider methods and packs results into map
  that goes to `ProcessingContext`.
- Implements specific data acquisition paths for individual handlers (e.g. different arguments passed to providers
  depending on step).

#### Processing Context (ProcessingContext)

- Shared container for data, flags and results during step execution.
- Contains i.a.: `gameSaveId`, `gameSaveStep`, `data` map (Provider results), `processFlags` (flow control) and
  `results` (processor results for persistence).
- Provides helper methods for retrieving single objects (`getSingleData`), lists (`getListData`) and processor results (
  `getResult`).

#### Processor Registry (StepProcessorRegistry)

- Stores and provides processors registered for given `StepType`.
- Ensures sorting by `getOrder()` and mapping `NextStep -> StepType`.

---

### Execution Flow (high level)

1. Application determines current step (`NextStep`) based on game state.
2. `GameSaveHandlerConfig` provides appropriate `GameSaveStepHandler` for this step.
3. Handler collects input data through `StepDataCollector` and builds `ProcessingContext`.
4. Handler gets `StepProcessor` list for step from `StepProcessorRegistry` (registered by `StepProcessorConfig` based on
   `@StepProcessorFor`).
5. Processors are executed in order resulting from `getOrder()`. Each processor:
    - reads data from `ProcessingContext.data` (Provider results),
    - can set `processFlags` and/or write `results`.
6. After processing completion handler persists changes (mapping results to entities, state update, determining next
   step).

---

### Class Diagram - Core (general relationships)
