package es.mde.miColegio;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import es.mde.miColegio.rest.ConfiguracionRest;
import org.springframework.context.annotation.PropertySource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource({ "classpath:configuracion/rest.properties", "classpath:configuracion/jackson.properties" })
@EnableTransactionManagement
@EnableJpaRepositories("${misRepositorios}") // leer valor de propiedades? pero solo para las entidades anotadas
@ComponentScan("es.mde.miColegio.rest")//para que escanee los Controller...
@Import(ConfiguracionRest.class)
public class ConfiguracionJava {
  @Value("${misEntidades}")
  String entidades;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env,
          JpaVendorAdapter vendorAdapter) {

      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);
//    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); // O pedirlo como parametro y que haga el Autowired
      em.setJpaVendorAdapter(vendorAdapter);

      em.setPackagesToScan(entidades); // leer valor de propiedades? pero solo para las entidades anotadas
      // em.setMappingResources("jpa/Usuario.orm.xml", "jpa/Cuaderno.orm.xml"); //para
      // escanear archivos xml...
      // leerValorDePropiedades?

      Properties jpaProperties = new Properties();
      Arrays.asList("dialect", "show_sql", "hbm2ddl.auto", "enable_lazy_load_no_trans") // leer valor de para las
                                                                                          // entidades anotadas
              .stream().map(s -> "hibernate." + s)
              .map(p -> new AbstractMap.SimpleEntry<String, String>(p, env.getProperty(p)))
              .filter(e -> e.getValue() != null).forEach(e -> jpaProperties.put(e.getKey(), e.getValue()));
      em.setJpaProperties(jpaProperties);

      return em;
  }

  @Bean
  public EntityManager entityManager(EntityManagerFactory emf) {
      System.err.println("--- LAS ENTIDADES MAPEADAS SON ---");
      emf.getMetamodel().getEntities().forEach(System.err::println);
      System.err.println("----------------------------------");

      return emf.createEntityManager();
  }

//Configuracion mixer
//  @Bean
//  public ObjectMapper getObjectMapper() {
//
//      ObjectMapper mapper = new ObjectMapper();
//      mapper.addMixIn(Cliente.class, MixIns.Clientes.class);
//      mapper.addMixIn(Libro.class, MixIns.Libros.class);
//      mapper.addMixIn(Cuaderno.class, MixIns.Cuadernos.class);
//
//      return mapper;
//  }
}
