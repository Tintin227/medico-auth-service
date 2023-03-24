package vn.uni.medico.shared.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application-${spring.profiles.active}.yaml"})
@EnableJpaRepositories(
        basePackages = "vn.uni.medico.auth.adapter.out.postgre.repo.admin",
        entityManagerFactoryRef = "adminEntityManager",
        transactionManagerRef = "adminTransactionManager"
)
@RequiredArgsConstructor
public class DatasourceAdminConfig {

    private final Environment env;

    @Bean(name = "adminEntityManager")
    public LocalContainerEntityManagerFactoryBean adminEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(adminDataSource());
        em.setPackagesToScan(
                new String[]{"vn.uni.medico.auth.domain.entity.admin"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource adminDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("spring.datasource-admin.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource-admin.url"));
        dataSource.setUsername(env.getProperty("spring.datasource-admin.username"));
        dataSource.setPassword(env.getProperty("spring.datasource-admin.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager adminTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                adminEntityManager().getObject());
        return transactionManager;
    }
}
