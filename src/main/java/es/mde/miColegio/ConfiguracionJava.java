package es.mde.miColegio;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Properties;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.mde.miColegio.rest.MixIns;
import es.mde.miColegio.seguridad.usuarios.Usuario;
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


/**
 * ConfiguracionJava va a establecer los parametros configurables en los distintos properties,
 * referenciándolos.
 *
 * @author JOSE LUIS PUENTES ALAMOS
 */

@Configuration
@PropertySource(
    {"classpath:configuracion/rest.properties", "classpath:configuracion/jackson.properties"})
@EnableTransactionManagement
@EnableJpaRepositories({"${misRepositorios}","es.mde.miColegio.seguridad.usuarios"})
@ComponentScan({"es.mde.miColegio.rest", "es.mde.miColegio.seguridad"})
@Import(ConfiguracionRest.class)
public class ConfiguracionJava {
  @Value("${misEntidades}")
  String entidades;
  @Value("${entidadSecurity}")
  String entidadSecurity;


  /**
   * Entity manager que sustituye al jpa-config.xml
   *
   * @param dataSource    Parametro del tipo DataSource
   * @param env           Parametro del tipo Environment
   * @param vendorAdapter Parametro del tipo JpaVendorAdapter
   * @return Devuelve un "@Bean" de LocalContainerEntityManagerFactory
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
      Environment env, JpaVendorAdapter vendorAdapter) {

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setJpaVendorAdapter(vendorAdapter);

    em.setPackagesToScan(entidades, entidadSecurity);

    Properties jpaProperties = new Properties();
    Arrays.asList("dialect", "show_sql", "hbm2ddl.auto", "enable_lazy_load_no_trans").stream()
        .map(s -> "hibernate." + s)
        .map(p -> new AbstractMap.SimpleEntry<String, String>(p, env.getProperty(p)))
        .filter(e -> e.getValue() != null)
        .forEach(e -> jpaProperties.put(e.getKey(), e.getValue()));
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

  @Bean
  public ObjectMapper getObjectMapper(){
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(Usuario.class, MixIns.Usuarios.class);
    return mapper;
  }
}
