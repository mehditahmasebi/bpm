package com.test.activiti.idgeneration;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.test.activiti.BaseTest;

public class IdGenerationTest extends BaseTest {

	Logger logger = Logger.getLogger(IdGenerationTest.class);
	public static final String KEY = "IdGeneration";
	
	@Override
	protected String[] getBpmnFiles() {
		return new String[] {"com/test/activiti/idgeneration/IdGeneration.bpmn"};
	}
	
	/**
	 * hadafam az test in bood ke:
	 * bebinam agar start yek process fail beshe aya dar hengame start process 2vom az ID tekrari estefade mikone ya na
	 * javae avalie : NA (yani id jadid mide)
	 * JAVABE ASLI : agar engine daghighan bad az in 2 start faile, down beshe va 2bare start beshe, mojadad az ID haye ghabli estefade mikoneh
	 * 					chon ke process e jadidi nasakhte, 
	 * 					vali agar ba'd az 2 state fail, yek start movafagh dashte bashim dige ID jadid mideh che engine stop o start beshe ya na
	 * natije inke Stable nist  
	 */
	@Test
	public void test()
	{
		try {
			runtimeService.startProcessInstanceByKey(KEY);
			assertNotNull(null,"RuntimeException must be thrown");
		} catch (Exception e) {		
		}
		try {
			runtimeService.startProcessInstanceByKey(KEY);
			assertNotNull(null,"RuntimeException must be thrown");
		} catch (Exception e) {		
		}
	}

}
