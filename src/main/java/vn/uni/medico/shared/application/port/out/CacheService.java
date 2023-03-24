package vn.uni.medico.shared.application.port.out;

public interface CacheService {
    void set(String key, String value);

    void set(String key, String value, Integer timeout);

    String get(String key);

    void remove(String key);

    void setHash(String hashName,String key,String value);

    String getValue(String hashName,String key);
}
