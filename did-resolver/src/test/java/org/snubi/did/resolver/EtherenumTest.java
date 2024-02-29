package org.snubi.did.resolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.utils.Convert;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("rawtypes")
@SpringBootTest
@Slf4j
public class EtherenumTest {
	
//	final String eUrl 				= "http://localhost:7545"; 		
//	final String contractAddress 	= "0x638E11B056eDCB62564b4aE052735ecf767FFedC";
//	final String account 			= "0x5a4ccfCAcfD21274A31b304Bb1EBCFD5FC438981";
//	final String privateKey 		= "0x570791ec1427ebb801056ae9a6cd68b943369eb50acf1ac8c3ec1e678df5f2b8";
	
	
	final String eUrl 				= "http://147.46.198.104:8501"; 		
	final String contractAddress 	= "0x60eCd21a77BCf44cb0d6C27100DbB3bF7fdB3c67";
	
	final String account 			= "0x245773b22f0306aee3f0085251a71c3785685ff1";
	final String privateKey 		= "12345678";
	
	
//	final String account 			= "0xfBD2Ac1848F031aa067ebF67d906C00d602714B1";
//	final String privateKey 		= "snubisnubi1004";
		
	Web3j web3j = Web3j.build(new HttpService(eUrl)); 
	
	@Test
	public void init() throws Exception {	
		//admin();
		creatDocuments();
		//getDocuments();
		//getLengthofDocuments();	
		//getAuthentication("did:avdid:b24162c7d8f2c4d");
		//getAuthentication2("did:avdid:b24162c7d8f2c4d");
	}
	public void creatDocuments() throws Exception {
		Function function = new Function("createDocument", Arrays.asList(
				new Utf8String("testid001"),
				new Utf8String("RsaVerificationKey2018"),
				new Utf8String("32u9r329wjj98f89usiuccc")
				), Arrays.asList());	
		if(sendAsyncDid(function)) {
			getLengthofDocuments();	
		}
		
		
	}
	public void getLengthofDocuments() throws Exception {
		Function function = new Function("getLengthofDocuments", Arrays.asList(), Arrays.asList(new TypeReference<Uint256>() {}));		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.account, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());  
        log.info("decode {} ", decode);   
        log.info("decode.get(0).getValue() {} ", decode.get(0).getValue());        
	}
	
	public void getDocuments() throws Exception {
		
		List<Type> inputParameters = Arrays.asList( new Address(this.account), new Uint256(0));
		List<TypeReference<?>> outputParameters =  Arrays.asList(
				new TypeReference<Address>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {}
		);
		
		Function function = new Function("documents", inputParameters, outputParameters );		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.account, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());  
        log.info("{} ", decode.get(0).getValue());     
        log.info("{} ", decode.get(1).getValue());     
        log.info("{} ", decode.get(2).getValue());     
        log.info("{} ", decode.get(3).getValue());     
	}
	
	public void getAuthentication2(String did) throws Exception {
		
		List<Type> inputParameters = Arrays.asList(new Utf8String(did), new Uint256(0));
		List<TypeReference<?>> outputParameters =  Arrays.asList(
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {}
		);
		
		Function function = new Function("authentications", inputParameters, outputParameters );		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.account, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());  
        log.info("{} ", decode.get(0).getValue());     
        log.info("{} ", decode.get(1).getValue());     
        log.info("{} ", decode.get(2).getValue());     
        log.info("{} ", decode.get(3).getValue());     
	}
	

	public void getAuthentication(String did) throws Exception {		
		
		List<Type> inputParameters = Arrays.asList(new Utf8String(did), new Uint256(0));
		//List<TypeReference<?>> outputParameters =  Arrays.<TypeReference<?>>asList(
//		List<TypeReference<?>> outputParameters =  Arrays.asList(
//				new TypeReference<DynamicArray<Utf8String>>() {},
//				new TypeReference<DynamicArray<Utf8String>>() {},
//				new TypeReference<DynamicArray<Utf8String>>() {},
//				new TypeReference<DynamicArray<Utf8String>>() {}
//		);
//		
//		List<TypeReference<?>> outputParameters2 =  Arrays.<TypeReference<?>>asList(
//				new TypeReference<Utf8String>() {},
//				new TypeReference<Utf8String>() {},
//				new TypeReference<Utf8String>() {},
//				new TypeReference<Utf8String>() {}
//				);
				// DynamicStruct
		//List<Type> claimableRewardsParams = Arrays.<Type>asList(new Address(credentials.getAddress())); 
		List<TypeReference<?>> outputParameters3 = Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Authentications>>() {}); 
//		final Function claimableRewardsFunction = new Function( "claimableRewards", claimableRewardsParams, claimableRewardsReturnTypes); 
//		String claimableRewardsEncodedFunction = FunctionEncoder .encode(claimableRewardsFunction); 
//		EthCall claimableRewardsResponse = web3.ethCall( Transaction.createEthCallTransaction(walletAddress, adamantRewardsContractAddress, claimableRewardsEncodedFunction), DefaultBlockParameterName.LATEST) .sendAsync().get(); 
//		List<Type> claimableRewardsSomeTypes = FunctionReturnDecoder.decode( claimableRewardsResponse.getValue(), claimableRewardsFunction.getOutputParameters()); 
//		
		//List<TypeReference<?>>  out =new ArrayList<Authentication>() {};
		
		 
		
		Function function = new Function("getAuthentication", inputParameters, outputParameters3 );	
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.account, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());  
       
        DynamicArray<Authentications> structArray = (DynamicArray<Authentications>) decode.get(0);
        log.info("decode {} ", decode.size());  
//        log.info("decode {} ", decode.get(0).getTypeAsString());  
//        log.info("decode {} ", decode.get(0).toString()); 
//        log.info("structArray {} ", structArray);
        
       
        for (Authentications dynamicStruct : structArray.getValue()) {
        	log.info("structArray id {} ", dynamicStruct.id);   
        	log.info("structArray authType {} ", dynamicStruct.authType);   
        	log.info("structArray controller {} ", dynamicStruct.controller);   
        	log.info("structArray publicKey {} ", dynamicStruct.publicKey);   
        }
        
//        List<Authentication> structArray =  (List<Authentication>) decode.get(0);
//        log.info("decode {} ", structArray.size());  
//        
        
//        List<Tuple4<String, String, String, String>> tupleArray = (List<Tuple4<String, String, String, String>>) decode.get(0);
//        for (Tuple4<String, String, String, String> tuple : tupleArray) {
//            String value1 = tuple.component1();
//            String value2 = tuple.component2();
//            String value3 = tuple.component3();
//            String value4 = tuple.component4();
//           
//            log.info("{},{},{},{} ", value1, value2, value3, value4);     
//        }
        
          
	}
	
	public void admin() {		
		
		 EthGetBalance balance = new EthGetBalance();
		 try {
			balance = this.web3j.ethGetBalance(account, DefaultBlockParameter.valueOf("latest"))
			        .sendAsync() 
			        .get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		BigDecimal balanceVaue = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER); 
		System.out.println("Account balance: " + balanceVaue);
		
	}
	
	
	
	private boolean sendAsyncDid(Function function) {
		try {
			
			BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
            System.out.println("Current Gas Price: " + gasPrice);
			
			log.info("sendAsynDao contractAddress : {}" , this.contractAddress );
			log.info("sendAsynDao function : {}" , function );
			log.info("sendAsynDao account : {}" , this.account );
			log.info("sendAsynDao privateKey : {}" ,  this.privateKey );
			Admin web3jAdmin = Admin.build(new HttpService(this.eUrl));
			PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(this.account, this.privateKey ).send();	
			log.info(".accountUnlocked {} " , personalUnlockAccount.accountUnlocked() );
			Web3j web3j = Web3j.build(new HttpService(this.eUrl));			
			String encodedFunction = FunctionEncoder.encode(function);
			//Nonce
		    EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(this.account, DefaultBlockParameterName.LATEST).sendAsync().get();
		    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    
//		    try {
//		  
//		    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(new Transaction(this.account, nonce, BigInteger.ZERO, BigInteger.ONE, this.contractAddress,  BigInteger.ZERO, encodedFunction))
//		    		.sendAsync().get();//.send();
//
//	        // Get the gas limit
//	        BigInteger gasLimit = ethEstimateGas.getAmountUsed();
//		   // System.out.println("Current Gas getResult: " + gasLimit);
//	        
//		    } catch (Exception e) {
//		    	System.out.println("Current Gas getResult: " + e.getMessage());
//		     }
//
//		    log.error("blockchain gasPrice : BigInteger.valueOf(6721975L)  {}" , BigInteger.valueOf(6721975L));
		    
		    //Run
		    EthSendTransaction ethCall = web3j.ethSendTransaction(
		            Transaction.createFunctionCallTransaction(
		            		this.account,
		                    nonce, //or nullL
		                    gasPrice, //gasPrice
		                    BigInteger.valueOf(6721975L), //gasLimit
		                    this.contractAddress,
		                    encodedFunction)
		    		).sendAsync().get();
	        
	        
		    //Thread.sleep(5000);
		    if (ethCall.getError() != null) {
		    	log.info("####### 블록체인 smart contract : ethCall.getError() :: " + ethCall.getError().getMessage() );
		    	log.error("error : " + ethCall.getError().getMessage());
		    	log.error("error : " + ethCall.getError().getData());
		    	log.error("error : " + ethCall.getError().getCode());
		    	
		    	while ( ethCall.getError().getMessage().equals("replacement transaction underpriced") == true ) {
		    		nonce = BigInteger.valueOf(nonce.longValue() + 1);
				   	ethCall = web3j.ethSendTransaction(
				   			Transaction.createFunctionCallTransaction(
				            		this.account,
				                    nonce, //or nullL
				                    gasPrice, //gasPrice
				                    BigInteger.valueOf(6721975L), //gasLimit
				                    this.contractAddress,
				                    encodedFunction)
				    		).sendAsync().get();
				   	if ( ethCall.getError() == null ) {
				   		log.error("Success!!");
				   		return true;
				   	}
				   	else {
				   		log.error(ethCall.getError().getMessage() + " , nonce : " + nonce);
				   		return false;
				   	}
			    }
		    }else {
		    	log.info("ethCall.getResult() SUCC!! :: " + ethCall.getTransactionHash() );
		    	
		    	return true;
		    }
		    
		    log.info(" ");
		    log.info(" ");
		} catch(Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			return false;
		}
		return false;
	}
	
	
	
	
	
	public static class Authentications extends DynamicStruct {
		public String id;
		public String authType;
		public String controller;
		public String publicKey;
		public Authentications(String id, String authType, String controller, String publicKey) {
		    super(new Utf8String(id), new Utf8String(authType), new Utf8String(controller), new Utf8String(publicKey));
		    this.id = id;
		    this.authType = authType;
		    this.controller = controller;
		    this.publicKey = publicKey;
		}
		public Authentications(Utf8String id, Utf8String authType, Utf8String controller, Utf8String publicKey) {
		    super(id, authType,controller,publicKey);
		    this.id = id.getValue();
		    this.authType = authType.getValue();
		    this.controller = controller.getValue();
		    this.publicKey = publicKey.getValue();
		  }
		}
	
	public static class Authentication {
        public Utf8String id;
        public Utf8String authType;
        public Utf8String controller;
        public Utf8String publicKey;
    }
	
	
	
	
	
	
	
	private boolean sendAsyncDidPayble(Function function) {
		try {			
			Admin web3jAdmin = Admin.build(new HttpService(this.eUrl));
			PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(this.account, this.privateKey ).send();
			// 가나슈 할때는 주석 잡아 놓자 
//			if (!personalUnlockAccount.accountUnlocked() || personalUnlockAccount.accountUnlocked() == null) {
//			    return;
//			}
			Web3j web3j = Web3j.build(new HttpService(this.eUrl));			
			String encodedFunction = FunctionEncoder.encode(function);
		    EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(this.account, DefaultBlockParameterName.LATEST).sendAsync().get();
		    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    EthSendTransaction ethCall = web3j.ethSendTransaction(
		            Transaction.createFunctionCallTransaction(
		            		this.account,
		                    nonce, 								//or nullL
		                    BigInteger.valueOf(3554698952L), //gasPrice
		                    BigInteger.valueOf(3000000L), //gasLimit
		                    this.contractAddress,
		                    new BigInteger("1000000000000000000"),
		                    encodedFunction)
		    		).sendAsync().get();
		    
		    //log.info("####### 블록체인 smart contract : ethCall.getResult() :: " + ethCall.getTransactionHash() );
		    if (ethCall.getError() != null) {
		    	log.info("####### 블록체인 smart contract : ethCall.getError() :: " + ethCall.getError().getMessage() );
		    	log.error("blockchain ethCall error : " + ethCall.getError().getMessage());
		    	while ( ethCall.getError().getMessage().equals("replacement transaction underpriced") == true ) {
		    		nonce = BigInteger.valueOf(nonce.longValue() + 1);
				   	ethCall = web3j.ethSendTransaction(
				   			Transaction.createFunctionCallTransaction(
				            		this.account,
				                    nonce, //or nullL
				                    BigInteger.valueOf(3554698952L), //gasPrice
				                    BigInteger.valueOf(3000000L), //gasLimit
				                    this.contractAddress,
				                    new BigInteger("1000000000000000000"),
				                    encodedFunction)
				    		).sendAsync().get();
				   	if ( ethCall.getError() == null ) break;
				   	else log.error(ethCall.getError().getMessage() + " , nonce : " + nonce);
			    }
		    }
		    
		    log.info("####### 블록체인 smart contract : ethCall.getResult() :: " + ethCall.getTransactionHash() );
		    if(ethCall.getTransactionHash() == null) {
		    	log.info("####### 블록체인 smart contract : ethCall.getResult() :: return false " + ethCall.getTransactionHash() );
		    	return false;
		    }else {
		    	log.info("####### 블록체인 smart contract : ethCall.getResult() :: return true " + ethCall.getTransactionHash() );
		    	return true;
		    }		    
		    
		} catch(Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			return false;
		}
	}
	
	
	
	

}

