package kata.configuration;

import kata.dao.DatabaseAccess;
import kata.dao.InMemoryDatabase;
import kata.model.BankClient;
import kata.services.AccountManager;
import kata.services.AccountManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableWebMvc
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    private final Map<String, DatabaseAccess> databaseAccessMap = new ConcurrentHashMap<>();

    @Autowired
    private BankClient bankClient;

    @Bean
    @RequestScope
    public AccountManager getAccountManager() {
        return new AccountManagerImpl();
    }

    @Bean
    @RequestScope
    public DatabaseAccess getDatabaseAccess() {
        return databaseAccessMap.computeIfAbsent(bankClient.getName(), (userId) -> new InMemoryDatabase());
    }

    @Bean
    @RequestScope
    public BankClient getBankClient() {
        return new BankClient();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/*");
    }
}
