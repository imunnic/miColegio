package es.mde.miColegio.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ServicioDatosIniciales {
    private ObjectMapper mapper = new ObjectMapper();

    public ServicioDatosIniciales(){
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      mapper.registerModule(new JavaTimeModule());
    }
  public <T> List<T> getDatosIniciales(String resourceName, TypeReference<List<T>> typeReference) {
    List<T> dataList = new ArrayList<>();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
      if (inputStream == null) {
        throw new FileNotFoundException("Resource not found: " + resourceName);
      }
      dataList = mapper.readValue(inputStream, typeReference);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataList;
  }
}
