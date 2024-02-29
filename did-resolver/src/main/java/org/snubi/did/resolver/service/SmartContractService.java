package org.snubi.did.resolver.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.snubi.did.resolver.common.ErrorCode;
import org.snubi.did.resolver.config.CustomConfig;
import org.snubi.did.resolver.dto.DidDto;
import org.snubi.did.resolver.dto.SignatureDto;
import org.snubi.did.resolver.entity.ChainNode;
import org.snubi.did.resolver.entity.Contract;
import org.snubi.did.resolver.exception.CustomException;
import org.snubi.did.resolver.repository.ChainNodeRepository;
import org.snubi.did.resolver.repository.ContractRepository;
import org.snubi.did.resolver.repository.DidRepository;
import org.snubi.did.resolver.struct.SmartContractStructs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class SmartContractService extends SmartContractStructs {	
	
	@Autowired DidRepository didRepository;	
	@Autowired ContractRepository contractRepository;
	@Autowired ChainNodeRepository chainNodeRepository;
	
	@AllArgsConstructor
	@Getter
	enum ContractName {		
		DID_REGISTRAR("did_registrar"),		
		SIGNATURE_LOG("signature_log");
		private final String name;	
	}
	

	protected String eUrl 					;
	protected String platformAccount		; 	
	protected String platformPassword 		;
	protected Web3j web3j 					;
	private int allCnt = 0;
	
	public void SmartContractServiceInit() {
		Optional<ChainNode> chainNode = chainNodeRepository.findByChainNodeSeq(CustomConfig.chainNodeNumber);		
		eUrl 					= chainNode.get().getNodeIp()+":"+chainNode.get().getNodePort();
		platformAccount			= chainNode.get().getAdminChainAddress(); 	
		platformPassword 		= chainNode.get().getAdminChainPassword();
		web3j 					= Web3j.build(new HttpService(eUrl));
	}
	
	/*
	    delete from tb_did;
		delete from tb_did_authentication;
		delete from tb_did_document;
		delete from tb_did_public_key;
		delete from tb_did_service;
	 * */
	
	public String getContractAddress(String contractName) {
		SmartContractServiceInit();
		Optional<Contract> contract = contractRepository.findByContractName(contractName);
		if(contract.isPresent()) {
			return  contract.get().getAddress(); 
		}
		return "";
	}
	
	public List<DynamicArray<Authentication>> getAuthentication(String did,DidDto didDto) throws Exception {	
		List<DynamicArray<Authentication>> list = new ArrayList<>();
		List<Type> inputParameters = Arrays.asList(new Utf8String(did));
		List<TypeReference<?>> outputParameters = Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Authentication>>() {}); 		
		Function function = new Function("getAuthentication", inputParameters, outputParameters );	
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());         
        for(int i=0;i < decode.size(); i++ ) {
        	DynamicArray<Authentication> structArray = (DynamicArray<Authentication>) decode.get(i); 
        	list.add(structArray);
            for (Authentication dynamicStruct : structArray.getValue()) {
            	log.info("Authentication id {} ", dynamicStruct.id);   
            	log.info("Authentication authType {} ", dynamicStruct.authType);   
            	log.info("Authentication controller {} ", dynamicStruct.controller);   
            	log.info("Authentication publicKey {} ", dynamicStruct.publicKey);   
            }  
        } 	
        this.web3j.shutdown();
        return list;
	}
	
	public List<DynamicArray<PublicKey>> getPublicKey(String did,DidDto didDto) throws Exception {	
		List<DynamicArray<PublicKey>> list = new ArrayList<>();
		List<Type> inputParameters = Arrays.asList(new Utf8String(did));
		List<TypeReference<?>> outputParameters = Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<PublicKey>>() {}); 		
		Function function = new Function("getPublicKey", inputParameters, outputParameters );	
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());         
        for(int i=0;i < decode.size(); i++ ) {
        	DynamicArray<PublicKey> structArray = (DynamicArray<PublicKey>) decode.get(i); 
        	list.add(structArray);
            for (PublicKey dynamicStruct : structArray.getValue()) {
            	log.info("PublicKey id {} ", dynamicStruct.id);   
            	log.info("PublicKey PublicKeyType {} ", dynamicStruct.PublicKeyType);   
            	log.info("PublicKey controller {} ", dynamicStruct.controller);   
            	log.info("PublicKey publicKey {} ", dynamicStruct.publicKey);   
            }  
        } 	
        this.web3j.shutdown();
        return list;
	}
	
	public List<DynamicArray<Service>> getServices(String did,DidDto didDto) throws Exception {	
		List<DynamicArray<Service>> list = new ArrayList<>();
		List<Type> inputParameters = Arrays.asList(new Utf8String(did));
		List<TypeReference<?>> outputParameters = Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Service>>() {}); 		
		Function function = new Function("getServices", inputParameters, outputParameters );	
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());         
        for(int i=0;i < decode.size(); i++ ) {
        	DynamicArray<Service> structArray = (DynamicArray<Service>) decode.get(i); 
        	list.add(structArray);
            for (Service dynamicStruct : structArray.getValue()) {
            	log.info("Service id {} ", dynamicStruct.id);   
            	log.info("Service ServiceType {} ", dynamicStruct.ServiceType);   
            	log.info("Service publicKey {} ", dynamicStruct.publicKey);   
            	log.info("Service serviceEndpoint {} ", dynamicStruct.serviceEndpoint);   
            }  
        } 	
        this.web3j.shutdown();
        return list;
	}
	
	public Integer getLengthofDocuments(DidDto didDto) throws Exception {
		Integer i = 0;
		log.info(" ");
		log.info(" ");
		log.info("getLengthofDocuments inputParameters getUserAccount {} ", didDto.getUserAccount()); 
		Function function = new Function("getLengthofDocuments", Arrays.asList(), Arrays.asList(new TypeReference<Uint256>() {}));		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST)
			.send();	
        int x  = 0;				
		if (i == 0) {
			while(true) {
				Thread.sleep(1000);
				ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST)
						.send(); 
				if (ethCall.getError() == null) {
					List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters()); 
			        //if(x % 50 == 1) 
			        log.info("while count {} :  getLengthofDocuments : {} ", x, decode.get(0).getValue());
			        i = Integer.parseInt(decode.get(0).getValue().toString());
			        x++;
			        if(i > 0) {
			        	return i;
		        	} else if(x > 2000000000) {
			        	throw new CustomException(ErrorCode.BLOCK_CHAIN_ERROR);	
				        
			        }else {
			        	continue;
			        }	
				}
				this.web3j.shutdown();
			}
		}else {
			log.info("? i : {} ", i);
			this.web3j.shutdown();
			return i;
		}	
	}
	
	public Integer seqs(SignatureDto signatureDto) throws Exception {
		// signatureDto.getToDid() > 원래 이코드인데 이건안됨 > 컨트렉에 getToDid 예전에 있었는데 지금 없음.
		// signatureDto.getFromDid() > 이건됨 
		SmartContractServiceInit();
		Integer i = 0;
		Function function = new Function("seqs", Arrays.asList( new Utf8String(signatureDto.getToDid()) ), Arrays.asList(new TypeReference<Uint256>() {}));		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.platformAccount, getContractAddress(ContractName.SIGNATURE_LOG.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
		
		int x  = 0;				
		if (i == 0) {
			while(true) {
				Thread.sleep(1000);
				ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( this.platformAccount, getContractAddress(ContractName.SIGNATURE_LOG.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
				if (ethCall.getError() == null) {
					List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters()); 
					//if(x % 50 == 1) 
					log.info("while count {} :  seqs : {} ", x, decode.get(0).getValue());
			        i = Integer.parseInt(decode.get(0).getValue().toString());
			        x++;
			        if(i > 0) {
			        	return i;
			        } else if(x > 2000000000) {
			        	throw new CustomException(ErrorCode.BLOCK_CHAIN_ERROR);	
			        }else {
			        	continue;
			        }	
				}
				this.web3j.shutdown();
			}
		}else {
			log.info("? i : {} ", i);
			this.web3j.shutdown();
			return i;
		}			
	}
	
	public List<String> getDocuments(DidDto didDto, int count) throws Exception {
		log.info("getDocuments {},{} ", didDto.getUserAccount(), count); 
		List<String> list = new ArrayList<>();
		List<Type> inputParameters = Arrays.asList( new Address(didDto.getUserAccount()), new Uint256(count));// , new Uint256(this.participantCnt) );//Arrays.asList(new Uint256(hashSeq), new Utf8String(contractStatus) );
		List<TypeReference<?>> outputParameters =  Arrays.asList(
				new TypeReference<Address>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {},
				new TypeReference<Utf8String>() {}
		);
		
		Function function = new Function("documents", inputParameters, outputParameters );		
		String encodedFunction = FunctionEncoder.encode(function);
		EthCall ethCall = this.web3j.ethCall(Transaction.createEthCallTransaction( didDto.getUserAccount(), getContractAddress(ContractName.DID_REGISTRAR.getName()), encodedFunction), DefaultBlockParameterName.LATEST).send();  
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),  function.getOutputParameters());  
        list.add(decode.get(0).getValue().toString());
        list.add(decode.get(1).getValue().toString());
        list.add(decode.get(2).getValue().toString());
        list.add(decode.get(3).getValue().toString());
        log.info("getDocuments address {} ", decode.get(0).getValue());     
        log.info("getDocuments did {} ", decode.get(1).getValue());     
        log.info("getDocuments created {} ", decode.get(2).getValue());     
        log.info("getDocuments updated {} ", decode.get(3).getValue());  
        log.info(" ");  
        this.web3j.shutdown();
        return list;
	}
	
	
	public boolean creatDocuments(DidDto didDto) throws Exception {
		Function function = new Function("createDocument", Arrays.asList(
				new Utf8String(didDto.getUserId()),
				new Utf8String(didDto.getAuthType()),
				new Utf8String(didDto.getAuthPublicKey())
				), Arrays.asList());	
		
		// sync 변경 
		return sendAsyncDid(function,didDto,ContractName.DID_REGISTRAR.getName());
	}
	
	public void setSignature(SignatureDto signatureDto) throws Exception {
		Function function = new Function("setSignature", Arrays.asList(
				new Utf8String(signatureDto.getServiceId()),
				new Utf8String(signatureDto.getFromDid()),
				new Utf8String(signatureDto.getToDid()),
				new Utf8String(signatureDto.getSignature())
				), Arrays.asList());	
		sendAsyncDid(function,null,ContractName.SIGNATURE_LOG.getName());
	}
	
	
	private boolean sendAsyncDid(Function function,DidDto didDto, String contractName) {
		try {		
			SmartContractServiceInit();
			String currentAccount = "";
			String currentPassword = "";			
			if(didDto == null) {
				currentAccount = this.platformAccount;
				currentPassword = this.platformPassword;
			}else {
				currentAccount = didDto.getUserAccount();
				currentPassword = didDto.getUserPassword();
			}	
			log.info(" ");
			log.info(" ");
			log.info("--- sendAsyncDid contractAddress : {}" , getContractAddress(contractName) );
			log.info("--- sendAsyncDid function : {}" 		, function );
			log.info("--- sendAsyncDid account : {}" 		, currentAccount);
			log.info("--- sendAsyncDid privateKey : {}" 		,  currentPassword );			
			log.info(" ");
			log.info(" ");
			
			Admin web3jAdmin = Admin.build(new HttpService(this.eUrl));
			PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(currentAccount, currentPassword ).send();			
			log.info("accountUnlocked {} " , personalUnlockAccount.accountUnlocked() );
			Web3j web3j = Web3j.build(new HttpService(this.eUrl));			
			String encodedFunction = FunctionEncoder.encode(function);
		    //EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(currentAccount, DefaultBlockParameterName.LATEST).sendAsync().get();
		    EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(currentAccount, DefaultBlockParameterName.LATEST).send();
		    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
		    log.info("blockchain gasPrice :  {}" , gasPrice);
		    log.info("blockchain contractName :  {}" , contractName);
		    EthSendTransaction ethCall = web3j.ethSendTransaction(
		            Transaction.createFunctionCallTransaction(
		            		currentAccount,
		                    nonce, //or nullL
		                    gasPrice, //gasPrice
		                    BigInteger.valueOf(6721975L), //gasLimit 6721975L or 500000L
		                    getContractAddress(contractName),
		                    encodedFunction)
		    		).send();
		    if (ethCall.getError() != null) {
		    	log.info("smart contract  ethCall error : " + ethCall.getError().getMessage() );
		    	log.info(" ");
		    	while ( ethCall.getError().getMessage().equals("replacement transaction underpriced") == true ) {
		    		nonce = BigInteger.valueOf(nonce.longValue() + 1);
				   	ethCall = web3j.ethSendTransaction(
				   			Transaction.createFunctionCallTransaction(
				   					currentAccount,
				                    nonce, //or nullL
				                    gasPrice, //gasPrice
				                    BigInteger.valueOf(6721975L), //gasLimit
				                    getContractAddress(contractName),
				                    encodedFunction)
				    		).send();
				   	if ( ethCall.getError() == null ) {
				   		return true;
				   	}
				   	else {
				   		log.error(ethCall.getError().getMessage() + " , nonce : " + nonce);
				   		return false;
				   	}
			    }
		    }else {
		    	log.info(" ");
				log.info(" ");
		    	log.info("ethCall.getResult() :: " + ethCall.getTransactionHash() );
		    	log.info(" ");
				log.info(" ");
				this.web3j.shutdown();
		    	return true;
		    }
		} catch(Exception ex) {
			this.web3j.shutdown();
			allCnt++;
			log.info(" connection reset catch block {} " , allCnt);
			if(allCnt < 2) {
				log.info(" sendAsyncDid recall {} " , allCnt);
				sendAsyncDid(function,didDto,contractName);
			}else{				
				log.error(ex.getMessage());
				return false;
			}			
		}
		return false;
	}

}
