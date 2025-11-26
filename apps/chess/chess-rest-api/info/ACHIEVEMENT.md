# Achievements system

## Achievement updates information

| ID  | Name                   | Process Updated                | Description                                                                                          |
|-----|------------------------|--------------------------------|------------------------------------------------------------------------------------------------------|
| 1   | Master of Titles       | SEASON_CLOSE, TOURNAMENT_CLOSE | Earn chess titles                                                                                    |
| 2   | Pawn Exterminator      | COMPLETE_EVENT                 | Capture all opponent's pawns                                                                         |
| 3   | Chess Strategist       | COMPLETE_EVENT                 | Deliver a check                                                                                      |
| 4   | Promotion Master       | COMPLETE_EVENT                 | Promote a large number of pieces                                                                     |
| 5   | Distinction Collector  | SEASON_CLOSE                   | Earn each of the Awards                                                                              |
| 6   | Mating Knight          | COMPLETE_EVENT                 | Checkmate with a knight                                                                              |
| 7   | Vertical Executioner   | COMPLETE_EVENT                 | Checkmate with a pawn                                                                                |
| 8   | Number One             | MONTH_CLOSE                    | Reach #1 in the rankings                                                                             |
| 9   | Checkless Victor       | COMPLETE_EVENT                 | Win without being checked                                                                            |
| 10  | Brave Against Odds     | COMPLETE_EVENT                 | Lose a game after 50 moves                                                                           |
| 11  | Match Veteran          | MONTH_CLOSE                    | Play a large number of games (counted at the end of the month)                                       |
| 12  | Unyielding             | MONTH_CLOSE                    | Play a large number of lost games (counted at the end of the month)                                  |
| 13  | Draw Strategist        | MONTH_CLOSE                    | Play a large number of drawn games (counted at the end of the month)                                 |
| 14  | Classical Player       | MONTH_CLOSE                    | Play a large number of classical games (counted at the end of the month)                             |
| 15  | Top 100 Rival          | MONTH_CLOSE                    | Play a large number of games against the top 100 ranked players (monthly count)                      |
| 16  | Black Warrior          | MONTH_CLOSE                    | Play a large number of games as Black (monthly count)                                                |
| 17  | League Player          | MONTH_CLOSE                    | Play a large number of league games (monthly count)                                                  |
| 18  | Challenger             | MONTH_CLOSE                    | Play a large number of Challenge tournament games (monthly count)                                    |
| 19  | Championship Contender | MONTH_CLOSE                    | Play a large number of World Championship games (monthly count)                                      |
| 20  | MASTERS Player         | MONTH_CLOSE                    | Play a large number of MASTERS tournament games (monthly count)                                      |
| 21  | Victor                 |                                | Achieve a high number of wins                                                                        |
| 22  | Winning Streak         |                                | Achieve a long winning streak                                                                        |
| 23  | Triumph with Black     |                                | Win many games as Black                                                                              |
| 24  | Tournament Champion    | TOURNAMENT_CLOSE               | Win many games in a single tournament                                                                |
| 25  | Grandmaster Slayer     |                                | Defeat grandmasters                                                                                  |
| 26  | Chess Triumph          |                                | Win by check                                                                                         |
| 27  | Classic Master         |                                | Win many classical games                                                                             |
| 28  | Flawless Victor        | COMPLETE_EVENT                 | Win without losing a piece                                                                           |
| 29  | Castling-Free Winner   | COMPLETE_EVENT                 | Win without castling                                                                                 |
| 30  | Queenless Winner       | COMPLETE_EVENT                 | Win without using the queen                                                                          |
| 31  | Medal Master           | TOURNAMENT_CLOSE               | Earn many medals                                                                                     |
| 32  | Trophy Collector       | TOURNAMENT_CLOSE               | Earn unique trophies                                                                                 |
| 33  | Gold Medalist          | TOURNAMENT_CLOSE               | Earn many gold medals                                                                                |
| 34  | Medal Defender         | TOURNAMENT_CLOSE               | Defend earned medals                                                                                 |
| 35  | K.O. Victor            | TOURNAMENT_CLOSE               | Win knockout tournaments                                                                             |
| 36  | League Victor          | TOURNAMENT_CLOSE               | Win leagues                                                                                          |
| 37  | Challenger             | TOURNAMENT_CLOSE               | Win Challenge tournaments                                                                            |
| 38  | Swiss Champion         | TOURNAMENT_CLOSE               | Win Swiss tournaments                                                                                |
| 39  | Cup Winner             | TOURNAMENT_CLOSE               | Win Cup tournaments                                                                                  |
| 40  | MASTERS Champion       | TOURNAMENT_CLOSE               | Win MASTERS tournaments                                                                              |
| 41  | Master MVP             | SEASON_CLOSE                   | Best player in the season ranking                                                                    |
| 42  | Master Consequencer    | SEASON_CLOSE                   | Most consecutive wins                                                                                |
| 43  | Master Total Winner    | SEASON_CLOSE                   | Most total wins                                                                                      |
| 44  | Master Trophier        | SEASON_CLOSE                   | "Most medals earned (based on medal count and medal value: 3-gold, 2-silver, 1-bronze, then ranked)" |
| 45  | Master Ratiolation     | SEASON_CLOSE                   | Best win/loss ratio                                                                                  |
| 46  | Master Battler         | SEASON_CLOSE                   | Most games player                                                                                    |
| 47  | Master Unbeatable      | SEASON_CLOSE                   | Hardest to beat (fewest losses in a season)                                                          |
| 48  | Master Hardbreaker     | SEASON_CLOSE                   | Most wins playing as Black                                                                           |
| 49  | Master Checkmasters    | SEASON_CLOSE                   | Most checkmates                                                                                      |
| 50  | Master Swisterlands    | SEASON_CLOSE                   | Most wins in Swiss games                                                                             |
| 51  | Seasoned Veteran       | SEASON_CLOSE                   | Complete many seasons                                                                                |
| 52  | Medal Player           | SEASON_CLOSE                   | Earn many medals in a single season                                                                  |
| 53  | Seasonal Standout      | SEASON_CLOSE                   | Receive many awards in a single season                                                               |
| 54  | Active Player          | SEASON_CLOSE                   | Play many games in a single season                                                                   |
| 55  | Seasonal Victor        | SEASON_CLOSE                   | Win many games in a single season                                                                    |
| 56  | Monthly Leader         | SEASON_CLOSE                   | Be MVP of the month in a season                                                                      |
| 57  | Loss Record Holder     | SEASON_CLOSE                   | Lose the fewest games in a season                                                                    |
| 58  | Draw Record Holder     | SEASON_CLOSE                   | Draw the fewest games in a season                                                                    |
| 59  | Grandmaster Challenge  | SEASON_CLOSE                   | Play many games against grandmasters in a single season                                              |
| 60  | Swiss Victor           | SEASON_CLOSE                   | Win many Swiss tournaments in a single season                                                        |
| 61  | Move Maker             | COMPLETE_EVENT                 | Make many moves                                                                                      |
| 62  | Castler                | COMPLETE_EVENT                 | Perform many castlings                                                                               |
| 63  | Speedy Pawn            | COMPLETE_EVENT                 | Make many double-step pawn moves                                                                     |
| 64  | Mobile King            | COMPLETE_EVENT                 | Move the king many times                                                                             |
| 65  | Black Operator         | COMPLETE_EVENT                 | Make many moves as Black                                                                             |
| 66  | White Operator         | COMPLETE_EVENT                 | Make many moves as White                                                                             |
| 67  | Queen Strategist       | COMPLETE_EVENT                 | Move the queen many times                                                                            |
| 68  | Agile Knight           | COMPLETE_EVENT                 | Move the knight many times                                                                           |
| 69  | Precise Bishop         | COMPLETE_EVENT                 | Move the bishop many times                                                                           |
| 70  | Mobile Rooktower       | COMPLETE_EVENT                 | Move the rook many times                                                                             |
| 71  | Piece Master           | COMPLETE_EVENT                 | Capture opponent's pieces                                                                            |
| 72  | White Hunter           | COMPLETE_EVENT                 | Capture opponent's white pieces                                                                      |
| 73  | Black Hunter           | COMPLETE_EVENT                 | Capture opponent's black pieces                                                                      |
| 74  | Queen Destroyer        | COMPLETE_EVENT                 | Capture opponent's queens                                                                            |
| 75  | Strong Piece Hunter    | COMPLETE_EVENT                 | "Capture opponent's strong pieces (queen, rook, bishop, knight)"                                     |
| 76  | Pawn Executioner       | COMPLETE_EVENT                 | Capture opponent's pawns                                                                             |
| 77  | Knight Hunter          | COMPLETE_EVENT                 | Capture opponent's knights                                                                           |
| 78  | Bishop Hunter          | COMPLETE_EVENT                 | Capture opponent's bishops                                                                           |
| 79  | Rook Hunter            | COMPLETE_EVENT                 | Capture opponent's rooks                                                                             |
| 80  | En Passant Master      | COMPLETE_EVENT                 | Capture pawns en passant                                                                             |
| 81  | Ranking Master         | MONTH_CLOSE                    | Highest ranking position                                                                             |
| 82  | Ranking Conqueror      | MONTH_CLOSE                    | Total rating points gained                                                                           |
| 83  | Higher Rated Slayer    | MONTH_CLOSE                    | Wins against higher-rated players (monthly count)                                                    |
| 84  | Monthly Ranker         | MONTH_CLOSE                    | Months without a rating drop                                                                         |
| 85  | Ranking Rise           | MONTH_CLOSE                    | Gain ranking positions (only upward movement counts)                                                 |
| 86  | Seasonal Ruler         | SEASON_CLOSE                   | Points earned in a single season                                                                     |
| 87  | Monthly Topper         | MONTH_CLOSE                    | Points earned in a single month                                                                      |
| 88  | Steady in Top 100      | MONTH_CLOSE                    | Top 100 in ranking for consecutive months                                                            |
| 89  | Minimal Scorer         | MONTH_CLOSE                    | At least 100 rating points in consecutive months                                                     |
| 90  | MVPoints Gatherer      | MONTH_CLOSE                    | More rating points earned than the second-best player                                                |
| 91  | Blitz Master           | COMPLETE_EVENT                 | Blitz games played                                                                                   |
| 92  | Black Conqueror        | COMPLETE_EVENT                 | Blitz wins as Black                                                                                  |
| 93  | Clock Tactician        | COMPLETE_EVENT                 | Blitz wins with more time left than opponent                                                         |
| 94  | Superspeed Winner      | COMPLETE_EVENT                 | Win in under 1 minute                                                                                |
| 95  | King of Rapid          | COMPLETE_EVENT                 | Win in under 10 minutes                                                                              |
| 96  | Opening Master         | COMPLETE_EVENT                 | Win in under 20 moves                                                                                |
| 97  | Chess Marathoner       | COMPLETE_EVENT                 | Games with at least 40 moves                                                                         |
| 98  | Lightning Chess        | COMPLETE_EVENT                 | Deliver check in under 5 moves                                                                       |
| 99  | Late Castling          | COMPLETE_EVENT                 | Castle only after move 10                                                                            |
| 100 | Unbeaten in Opening    | COMPLETE_EVENT                 | Don’t lose a piece in the first 10 moves                                                             |

## Counter information

### Updates
| ID | Name              | Process Updated | Description                                      |
|----|-------------------|-----------------|--------------------------------------------------|
| 56 | Monthly Leader    | MONTH_CLOSE     | Be MVP of the month in a season                  |
| 86 | Seasonal Ruler    | MONTH_CLOSE     | Points earned in a single season                 |
| 87 | Monthly Topper    | MONTH_CLOSE     | Points earned in a single month                  |
| 88 | Steady in Top 100 | MONTH_CLOSE     | Top 100 in ranking for consecutive months        |
| 89 | Minimal Scorer    | MONTH_CLOSE     | At least 100 rating points in consecutive months |


### Updated via Game App
| ID  | Name                 | Process Updated | Description                                                      |
|-----|----------------------|-----------------|------------------------------------------------------------------|
| 2   | Pawn Exterminator    | CHESS-GAME-APP  | Capture all opponent's pawns                                     |
| 3   | Chess Strategist     | CHESS-GAME-APP  | Deliver a check                                                  |
| 4   | Promotion Master     | CHESS-GAME-APP  | Promote a large number of pieces                                 |
| 6   | Mating Knight        | CHESS-GAME-APP  | Checkmate with a knight                                          |
| 7   | Vertical Executioner | CHESS-GAME-APP  | Checkmate with a pawn                                            |
| 9   | Checkless Victor     | CHESS-GAME-APP  | Win without being checked                                        |
| 10  | Brave Against Odds   | CHESS-GAME-APP  | Lose a game after 50 moves                                       |
| 28  | Flawless Victor      | CHESS-GAME-APP  | Win without losing a piece                                       |
| 29  | Castling-Free Winner | CHESS-GAME-APP  | Win without castling                                             |
| 30  | Queenless Winner     | CHESS-GAME-APP  | Win without using the queen                                      |
| 61  | Move Maker           | CHESS-GAME-APP  | Make many moves                                                  |
| 62  | Castler              | CHESS-GAME-APP  | Perform many castlings                                           |
| 63  | Speedy Pawn          | CHESS-GAME-APP  | Make many double-step pawn moves                                 |
| 64  | Mobile King          | CHESS-GAME-APP  | Move the king many times                                         |
| 65  | Black Operator       | CHESS-GAME-APP  | Make many moves as Black                                         |
| 66  | White Operator       | CHESS-GAME-APP  | Make many moves as White                                         |
| 67  | Queen Strategist     | CHESS-GAME-APP  | Move the queen many times                                        |
| 68  | Agile Knight         | CHESS-GAME-APP  | Move the knight many times                                       |
| 69  | Precise Bishop       | CHESS-GAME-APP  | Move the bishop many times                                       |
| 70  | Mobile Rooktower     | CHESS-GAME-APP  | Move the rook many times                                         |
| 71  | Piece Master         | CHESS-GAME-APP  | Capture opponent's pieces                                        |
| 72  | White Hunter         | CHESS-GAME-APP  | Capture opponent's white pieces                                  |
| 73  | Black Hunter         | CHESS-GAME-APP  | Capture opponent's black pieces                                  |
| 74  | Queen Destroyer      | CHESS-GAME-APP  | Capture opponent's queens                                        |
| 75  | Strong Piece Hunter  | CHESS-GAME-APP  | "Capture opponent's strong pieces (queen, rook, bishop, knight)" |
| 76  | Pawn Executioner     | CHESS-GAME-APP  | Capture opponent's pawns                                         |
| 77  | Knight Hunter        | CHESS-GAME-APP  | Capture opponent's knights                                       |
| 78  | Bishop Hunter        | CHESS-GAME-APP  | Capture opponent's bishops                                       |
| 79  | Rook Hunter          | CHESS-GAME-APP  | Capture opponent's rooks                                         |
| 80  | En Passant Master    | CHESS-GAME-APP  | Capture pawns en passant                                         |
| 91  | Blitz Master         | CHESS-GAME-APP  | Blitz games played                                               |
| 92  | Black Conqueror      | CHESS-GAME-APP  | Blitz wins as Black                                              |
| 93  | Clock Tactician      | CHESS-GAME-APP  | Blitz wins with more time left than opponent                     |
| 94  | Superspeed Winner    | CHESS-GAME-APP  | Win in under 1 minute                                            |
| 95  | King of Rapid        | CHESS-GAME-APP  | Win in under 10 minutes                                          |
| 96  | Opening Master       | CHESS-GAME-APP  | Win in under 20 moves                                            |
| 97  | Chess Marathoner     | CHESS-GAME-APP  | Games with at least 40 moves                                     |
| 98  | Lightning Chess      | CHESS-GAME-APP  | Deliver check in under 5 moves                                   |
| 99  | Late Castling        | CHESS-GAME-APP  | Castle only after move 10                                        |
| 100 | Unbeaten in Opening  | CHESS-GAME-APP  | Don’t lose a piece in the first 10 moves                         |


### Resets
| ID  | Name                 | Process Updated | Description                                                      |
|-----|----------------------|-----------------|------------------------------------------------------------------|
| 2   | Pawn Exterminator    | COMPLETE_EVENT  | Capture all opponent's pawns                                     |
| 3   | Chess Strategist     | COMPLETE_EVENT  | Deliver a check                                                  |
| 4   | Promotion Master     | COMPLETE_EVENT  | Promote a large number of pieces                                 |
| 6   | Mating Knight        | COMPLETE_EVENT  | Checkmate with a knight                                          |
| 7   | Vertical Executioner | COMPLETE_EVENT  | Checkmate with a pawn                                            |
| 9   | Checkless Victor     | COMPLETE_EVENT  | Win without being checked                                        |
| 10  | Brave Against Odds   | COMPLETE_EVENT  | Lose a game after 50 moves                                       |
| 28  | Flawless Victor      | COMPLETE_EVENT  | Win without losing a piece                                       |
| 29  | Castling-Free Winner | COMPLETE_EVENT  | Win without castling                                             |
| 30  | Queenless Winner     | COMPLETE_EVENT  | Win without using the queen                                      |
| 56  | Monthly Leader       | SEASON_START    | Be MVP of the month in a season                                  |
| 61  | Move Maker           | COMPLETE_EVENT  | Make many moves                                                  |
| 62  | Castler              | COMPLETE_EVENT  | Perform many castlings                                           |
| 63  | Speedy Pawn          | COMPLETE_EVENT  | Make many double-step pawn moves                                 |
| 64  | Mobile King          | COMPLETE_EVENT  | Move the king many times                                         |
| 65  | Black Operator       | COMPLETE_EVENT  | Make many moves as Black                                         |
| 66  | White Operator       | COMPLETE_EVENT  | Make many moves as White                                         |
| 67  | Queen Strategist     | COMPLETE_EVENT  | Move the queen many times                                        |
| 68  | Agile Knight         | COMPLETE_EVENT  | Move the knight many times                                       |
| 69  | Precise Bishop       | COMPLETE_EVENT  | Move the bishop many times                                       |
| 70  | Mobile Rooktower     | COMPLETE_EVENT  | Move the rook many times                                         |
| 71  | Piece Master         | COMPLETE_EVENT  | Capture opponent's pieces                                        |
| 72  | White Hunter         | COMPLETE_EVENT  | Capture opponent's white pieces                                  |
| 73  | Black Hunter         | COMPLETE_EVENT  | Capture opponent's black pieces                                  |
| 74  | Queen Destroyer      | COMPLETE_EVENT  | Capture opponent's queens                                        |
| 75  | Strong Piece Hunter  | COMPLETE_EVENT  | "Capture opponent's strong pieces (queen, rook, bishop, knight)" |
| 76  | Pawn Executioner     | COMPLETE_EVENT  | Capture opponent's pawns                                         |
| 77  | Knight Hunter        | COMPLETE_EVENT  | Capture opponent's knights                                       |
| 78  | Bishop Hunter        | COMPLETE_EVENT  | Capture opponent's bishops                                       |
| 79  | Rook Hunter          | COMPLETE_EVENT  | Capture opponent's rooks                                         |
| 80  | En Passant Master    | COMPLETE_EVENT  | Capture pawns en passant                                         |
| 88  | Steady in Top 100    | MONTH_CLOSE     | Top 100 in ranking for consecutive months                        |
| 89  | Minimal Scorer       | MONTH_CLOSE     | At least 100 rating points in consecutive months                 |
| 91  | Blitz Master         | COMPLETE_EVENT  | Blitz games played                                               |
| 92  | Black Conqueror      | COMPLETE_EVENT  | Blitz wins as Black                                              |
| 93  | Clock Tactician      | COMPLETE_EVENT  | Blitz wins with more time left than opponent                     |
| 94  | Superspeed Winner    | COMPLETE_EVENT  | Win in under 1 minute                                            |
| 95  | King of Rapid        | COMPLETE_EVENT  | Win in under 10 minutes                                          |
| 96  | Opening Master       | COMPLETE_EVENT  | Win in under 20 moves                                            |
| 97  | Chess Marathoner     | COMPLETE_EVENT  | Games with at least 40 moves                                     |
| 98  | Lightning Chess      | COMPLETE_EVENT  | Deliver check in under 5 moves                                   |
| 99  | Late Castling        | COMPLETE_EVENT  | Castle only after move 10                                        |
| 100 | Unbeaten in Opening  | COMPLETE_EVENT  | Don’t lose a piece in the first 10 moves                         |
