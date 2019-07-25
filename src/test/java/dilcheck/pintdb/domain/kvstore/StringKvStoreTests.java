package dilcheck.pintdb.domain.kvstore;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class StringKvStoreTests {
  private static final StringKvStore stringKvStore = new StringKvStore();
  private static String VALUE1;
  private static String VALUE2;

  /**
   * test environment setup.
   */
  @BeforeClass
  public static void setup() {
    VALUE1 = "getTest";
    VALUE2 = "deleteTest";

    stringKvStore.set("get", VALUE1);
    stringKvStore.set("delete", VALUE2);
  }

  @Test
  public void getTest() {
    String actual = stringKvStore.get("get");

    assertEquals(VALUE1, actual);
  }

  @Test(expected = NullPointerException.class)
  public void getExceptionTest() {
    stringKvStore.get(null);
  }

  @Test
  public void setTest() {
    final String key = "key";
    final String expected = "value";

    stringKvStore.set(key, expected);
    final String actual = stringKvStore.get(key);

    assertEquals(expected, actual);
  }

  @Test(expected = NullPointerException.class)
  public void setExceptionTest() {
    stringKvStore.set(null, "value");
  }

  @Test
  public void deleteTest() {
    final String key = "delete";
    final String expected = null;

    stringKvStore.delete(key);
    final String actual = stringKvStore.get(key);

    assertEquals(expected, actual);
  }

  @Test(expected = NullPointerException.class)
  public void deleteExceptionTest() {
    stringKvStore.delete(null);
  }
}
