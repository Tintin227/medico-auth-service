package vn.uni.medico.shared.config;

public class TenantContext {

    public static final String DEFAULT_TENANT_ID = "public";
    private static ThreadLocal<Long> currentTenant = new ThreadLocal<Long>();
    private static ThreadLocal<Long> currentBranch = new ThreadLocal<Long>();

    public static void setCurrentTenant(Long tenant) {
        currentTenant.set(tenant);
    }

    public static Long getCurrentTenant() {
        return currentTenant.get();
    }
    public static void setCurrentBranch(Long branch) {
        currentBranch.set(branch);
    }

    public static Long getCurrentBranch() {
        return currentBranch.get();
    }
    public static void clear() {
        currentTenant.remove();
        currentBranch.remove();
    }
}
