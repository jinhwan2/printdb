package dilcheck.pintdb.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import dilcheck.pintdb.domain.config.LoadConfig;
import dilcheck.pintdb.exec.PintDbLauncher;
import dilcheck.pintdb.model.RequestForm;
import dilcheck.pintdb.service.SaveDatabaseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SuppressWarnings("rawtypes")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = {PintDbLauncher.class,
    DatabaseController.class, SaveDatabaseService.class, LoadConfig.class})
public class DatabaseControllerRestTest {
  public static final String ENDPOINT = "http://localhost:2080/api/v1";

  @Test
  public void stringIntegrationRestTest() {
    final RestTemplate restTemplate = new RestTemplate();

    final String key = "rest string key";
    final String testEndpoint = ENDPOINT + "/strings";
    final String value = "test";

    // post
    RequestForm requestForm = new RequestForm();
    requestForm.setKey(key);
    requestForm.setValue(value);
    restTemplate.postForEntity(testEndpoint, requestForm, Map.class);

    // get
    ResponseEntity<Map> resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    final Object actual = resultMap.getBody().get("data");
    assertEquals(value, actual);

    // delete
    restTemplate.delete(testEndpoint + "/" + key, Map.class);
    resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    final Object nullValue = resultMap.getBody().get("data");
    assertNull(nullValue);
  }

  @Test
  public void restListIntegrationTest() {
    final RestTemplate restTemplate = new RestTemplate();

    final String key = "rest list key";
    final String testEndpoint = ENDPOINT + "/lists";
    final List<Object> value = new ArrayList<Object>();
    value.add("value1");
    value.add("value2");

    // post
    RequestForm requestForm = new RequestForm();
    requestForm.setKey(key);
    requestForm.setValue(value);
    restTemplate.postForEntity(testEndpoint, requestForm, Map.class);

    // get
    ResponseEntity<Map> resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    final Object actual = resultMap.getBody().get("data");
    assertEquals(value, actual);

    // delete
    restTemplate.delete(testEndpoint + "/" + key, Map.class);
    resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    final Object nullValue = resultMap.getBody().get("data");
    assertNull(nullValue);
  }

  @Test
  public void hashMapIntegrationRestTest() {
    final RestTemplate restTemplate = new RestTemplate();

    final String key = "rest hashMap key";
    final String testEndpoint = ENDPOINT + "/hashmaps";
    final HashMap<String, Object> value = new HashMap<String, Object>();
    value.put("key1", "value1");
    value.put("key2", "value2");

    // post
    RequestForm requestForm = new RequestForm();
    requestForm.setKey(key);
    requestForm.setValue(value);
    restTemplate.postForEntity(testEndpoint, requestForm, Map.class);

    // get
    ResponseEntity<Map> resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    @SuppressWarnings("unchecked")
    final HashMap<String, Object> actual =
        (HashMap<String, Object>) resultMap.getBody().get("data");
    assertEquals(value.get("key1"), actual.get("key1"));

    // delete
    restTemplate.delete(testEndpoint + "/" + key, Map.class);
    resultMap = restTemplate.getForEntity(testEndpoint + "/" + key, Map.class);
    final Object nullValue = resultMap.getBody().get("data");
    assertNull(nullValue);
  }
}
