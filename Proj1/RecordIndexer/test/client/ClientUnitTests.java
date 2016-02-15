package client;

import org.junit.*;

import Server.server.server;
import static org.junit.Assert.*;

public class ClientUnitTests {
	
	private static server testServer;
	
	@Before
	public void setup() {
		testServer.main(null);
	}
	
	@After
	public void teardown() {
		if(testServer != null) {
			testServer.stop();
			
		}
		testServer = null;
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests",
				"client.unitTests.testValidate",
				"client.unitTests.testGetProj",
				"client.unitTests.testSampleImage",
				"client.unitTests.testGetBatch",
				"client.unitTests.testSubmitBatch",
				"client.unitTests.testQualityCheck"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

