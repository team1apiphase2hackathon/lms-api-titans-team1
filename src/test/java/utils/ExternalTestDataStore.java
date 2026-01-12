package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalTestDataStore {

	private static final Path FILE_PATH =
	        Paths.get("src/test/resources/runtime-data/testdata.json");

	    private static final ObjectMapper mapper = new ObjectMapper();
	    private static final Object lock = new Object();

	    public static void put(String key, String value) {
	        synchronized (lock) {
	            try {
	                Map<String, String> data = readAll();
	                data.put(key, value);
	                mapper.writerWithDefaultPrettyPrinter()
	                      .writeValue(FILE_PATH.toFile(), data);
	            } catch (IOException e) {
	                throw new RuntimeException("Failed to write test data", e);
	            }
	        }
	    }

	    public static String get(String key) {
	        synchronized (lock) {
	            return readAll().get(key);
	        }
	    }

	    private static Map<String, String> readAll() {
	        try {
	            if (!Files.exists(FILE_PATH)) {
	                return new HashMap<>();
	            }
	            return mapper.readValue(
	                FILE_PATH.toFile(),
	                new TypeReference<Map<String, String>>() {}
	            );
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to read test data", e);
	        }
	    }
	    
}
