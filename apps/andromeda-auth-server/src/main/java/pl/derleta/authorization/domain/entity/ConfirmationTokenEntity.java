package pl.derleta.authorization.domain.entity;

import java.sql.Timestamp;

public class ConfirmationTokenEntity {

    private long tokenId;
    private UserEntity user;
    private String token;
    private Timestamp expirationDate;

    public ConfirmationTokenEntity() {
    }

    public ConfirmationTokenEntity(long tokenId, UserEntity user, String token, Timestamp expirationDate) {
        this.tokenId = tokenId;
        this.user = user;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmationTokenEntity that)) return false;
        if (tokenId != that.tokenId) return false;
        if (!user.equals(that.user)) return false;
        if (!token.equals(that.token)) return false;
        return expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(tokenId);
        result = 31 * result + user.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + expirationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ConfirmationTokenEntity{" +
                "tokenId=" + tokenId +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
