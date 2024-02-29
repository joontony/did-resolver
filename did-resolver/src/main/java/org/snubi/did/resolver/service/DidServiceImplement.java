package org.snubi.did.resolver.service;

import java.util.List;
import java.util.Optional;
import org.snubi.did.resolver.dto.AuthenticationDto;
import org.snubi.did.resolver.dto.DidDto;
import org.snubi.did.resolver.dto.DidSubDto;
import org.snubi.did.resolver.dto.PublicKeyDto;
import org.snubi.did.resolver.dto.ResponseDidDto;
import org.snubi.did.resolver.dto.ResponseSignatureDto;
import org.snubi.did.resolver.dto.ServiceDto;
import org.snubi.did.resolver.dto.SignatureDto;
import org.snubi.did.resolver.entity.Did;
import org.snubi.did.resolver.entity.DidAuthentication;
import org.snubi.did.resolver.entity.DidDocument;
import org.snubi.did.resolver.entity.DidPublicKey;
import org.snubi.did.resolver.entity.DidService;
import org.snubi.did.resolver.entity.Signature;
import org.snubi.did.resolver.entity.SignatureClaim;
import org.snubi.did.resolver.entity.User;
import org.snubi.did.resolver.repository.ContractRepository;
import org.snubi.did.resolver.repository.DidAuthenticationRepository;
import org.snubi.did.resolver.repository.DidDocumentRepository;
import org.snubi.did.resolver.repository.DidPublicKeyRepository;
import org.snubi.did.resolver.repository.DidRepository;
import org.snubi.did.resolver.repository.DidServiceRepository;
import org.snubi.did.resolver.repository.SignatureClaimRepository;
import org.snubi.did.resolver.repository.SignatureRepository;
import org.snubi.did.resolver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.DynamicArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DidServiceImplement extends SmartContractService implements DidApiService {
	
	private final DidRepository didRepository;
	private final ContractRepository contractRepository;
	private final UserRepository userRepository;
	private final DidAuthenticationRepository didAuthenticationRepository;
	private final DidDocumentRepository didDocumentRepository;
	private final DidPublicKeyRepository didPublicKeyRepository;
	private final DidServiceRepository didServiceRepository;
	private final SignatureRepository signatureRepository;
	private final SignatureClaimRepository signatureClaimRepository;
	
	@Transactional
	@Override
	public ResponseDidDto createDocument(DidDto didDto) throws Exception {	
		log.info("--------------------------------------------");
		log.info("smartcontract : createDocument ");
		log.info("--------------------------------------------");
		
		
		Optional<User> userCheck = userRepository.findByUserId(didDto.getUserId());
		if(userCheck.isEmpty()) {
			User userInit = User.builder()
					.userId(didDto.getUserId())
					.userAddress(didDto.getUserAccount())
					.build();
			userRepository.save(userInit);			
		}
		Optional<User> user = userRepository.findByUserId(didDto.getUserId());
		
		log.info("--------------------------------------------");
		log.info("smartcontract : user = {}",user.get().getUserId());
		log.info("creatDocuments > getLengthofDocuments > getDocuments");
		log.info("--------------------------------------------");	
		
		if(creatDocuments(didDto)) {
			log.info("--------------------------------------------");
			log.info("createDocument");
			log.info("--------------------------------------------");
			Integer docLastnum = getLengthofDocuments(didDto) - 1;
			List<String> doc = getDocuments(didDto,docLastnum);					
			log.info("--------------------------------------------");
			log.info("getLengthofDocuments : docLastnum = {}",docLastnum);
			log.info("getDocuments : did = {}",doc.get(1));
			log.info("--------------------------------------------");	
			
			String[] didArray = doc.get(1).split(":");
			Did did = Did.builder()
					.contract(contractRepository.findByContractSeq(1L).get())
					.user(user.get())
					.did(doc.get(1))
					.identifier(didArray[2])
					.method(didArray[1])
					.build();		
			didRepository.save(did);	
			
			
			log.info("--------------------------------------------");
			log.info("smartcontract : didRepository save {}", did);
			log.info("--------------------------------------------");			
			DidDocument didDocument = DidDocument.builder()
					.did(did)
					.context("https://www.w3.org/ns/did/v1,https://w3id.org/security/v1")
					.build();
			didDocumentRepository.save(didDocument);	
			log.info("--------------------------------------------");
			log.info("smartcontract : didDocumentRepository save {}", didDocument);
			log.info("--------------------------------------------");	
			
			log.info("smartcontract : getAuthentication");
			List<DynamicArray<Authentication>> authentications = getAuthentication(did.getDid(),didDto);
			for(DynamicArray<Authentication> item : authentications) {	
	            for (Authentication dynamicStruct : item.getValue()) {
	            	DidAuthentication didAuthentication = DidAuthentication.builder()
	            			.didDocument(didDocument)
	        				.authId(dynamicStruct.id)
	        				.authType(dynamicStruct.authType)
	        				.controller(dynamicStruct.controller)
	        				.publicKey(dynamicStruct.publicKey)
	        				.build();
	            	didAuthenticationRepository.save(didAuthentication);
	            }  
	        } 	
			log.info("smartcontract : getPublicKey");
			List<DynamicArray<PublicKey>> publicKeys = getPublicKey(did.getDid(),didDto);
			for(DynamicArray<PublicKey> item : publicKeys) {	
	            for (PublicKey dynamicStruct : item.getValue()) {
	            	
	            	DidPublicKey didPublicKey =  DidPublicKey.builder()
	            			.didDocument(didDocument)
	            			.pkId(dynamicStruct.id)
	            			.publicKeyType(dynamicStruct.PublicKeyType)
	            			.publicKey(dynamicStruct.publicKey)
	            			.controller(dynamicStruct.controller)
	            			.build();            	
	            	didPublicKeyRepository.save(didPublicKey);
	            }  
	        } 	
			log.info("smartcontract : getServices");
			List<DynamicArray<Service>> services = getServices(did.getDid(),didDto);
			for(DynamicArray<Service> item : services) {	
	            for (Service dynamicStruct : item.getValue()) {
	            	DidService didService = DidService.builder()
	            			.didDocument(didDocument)
	            			.serviceId(dynamicStruct.id)
	            			.serviceType(dynamicStruct.ServiceType)
	            			.serviceEndpoint(dynamicStruct.serviceEndpoint)
	            			.publicKey(dynamicStruct.publicKey)
	            			.build();
	            	didServiceRepository.save(didService);
	            }  
	        } 	
			
		return ResponseDidDto.builder().did(doc.get(1)).build();
		}else {
			return null;
		}
		

	}
	
	@Override
	public boolean upsertAuthentication(AuthenticationDto authenticationDto) {
		Optional<Did> did = didRepository.findByDid(authenticationDto.getDid());
		if(did.isPresent()) {			
			Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(did.get().getDidSeq());
			if(didDocument.isPresent()) {	
				Optional<DidAuthentication> didAuthentication = didAuthenticationRepository.findByDidDocument_DidDocumentSeq(didDocument.get().getDidDocumentSeq());				
				if(didAuthentication.isPresent()) {	
					didAuthentication.get().updateAuthentication(authenticationDto.getAuthId(),authenticationDto.getAuthType(),authenticationDto.getPublicKey(),authenticationDto.getController());				
		        	didAuthenticationRepository.save(didAuthentication.get());	        	
		        	return true;
				}				
			}	
		}
		return false;
	}
	
	@Override
	public boolean upsertPublicKey(PublicKeyDto publicKeyDto) {
		Optional<Did> did = didRepository.findByDid(publicKeyDto.getDid());
		if(did.isPresent()) {			
			Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(did.get().getDidSeq());
			if(didDocument.isPresent()) {	
				Optional<DidPublicKey> didPublicKey = didPublicKeyRepository.findByDidDocument_DidDocumentSeq(didDocument.get().getDidDocumentSeq());				
				if(didPublicKey.isPresent()) {	
					didPublicKey.get().updatePublicKey(publicKeyDto.getPublicKeyId(),publicKeyDto.getPublicKeyType(),publicKeyDto.getPublicKey(),publicKeyDto.getController());				
					didPublicKeyRepository.save(didPublicKey.get());	        	
		        	return true;
				}				
			}	
		}
		return false;
	}
	
	@Override
	public boolean upsertService(ServiceDto serviceDto) {
		Optional<Did> did = didRepository.findByDid(serviceDto.getDid());
		if(did.isPresent()) {			
			Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(did.get().getDidSeq());
			if(didDocument.isPresent()) {	
				Optional<DidService> didService = didServiceRepository.findByDidDocument_DidDocumentSeqAndServiceId(didDocument.get().getDidDocumentSeq(),serviceDto.getServiceId());				
				if(didService.isPresent()) {	
					didService.get().updateService(serviceDto.getServiceType(),serviceDto.getPublicKey(),serviceDto.getServiceEndpoint());				
					didServiceRepository.save(didService.get());	        	
		        	return true;
				}else {
					DidService clsDidService = DidService.builder()
	            			.didDocument(didDocument.get())
	            			.serviceId(serviceDto.getServiceId())
	            			.serviceType(serviceDto.getServiceType())
	            			.serviceEndpoint(serviceDto.getServiceEndpoint())
	            			.publicKey(serviceDto.getPublicKey())
	            			.build();
	            	didServiceRepository.save(clsDidService);
	            	return true;
				}				
			}	
		}
		return false;
	}

	@Override
	public ResponseSignatureDto createSignature(SignatureDto signatureDto) throws Exception {		
		log.info("--------------------------------------------");
		log.info("smartcontract : setSignature ");
		log.info("--------------------------------------------");
		setSignature(signatureDto);		
		Thread.sleep(10000);
		Integer signatureSeq = seqs(signatureDto);	
		
		Signature signature = Signature.builder()
				.contract(contractRepository.findByContractSeq(2L).get())
				.signSeq(signatureSeq)
				.serviceId(signatureDto.getServiceId())
				.fromDid(signatureDto.getFromDid())
				.toDid(signatureDto.getToDid())
				.signature(signatureDto.getSignature())
				.build();		
		signatureRepository.save(signature);
		
		SignatureClaim signatureClaim = SignatureClaim.builder()
				.signature(signature)
				.claimId(signatureDto.getClaimId())
				.build();		
		signatureClaimRepository.save(signatureClaim);
		log.info("--------------------------------------------");
		log.info("smartcontract : setSignature return signatureSeq {}", signatureSeq);
		log.info("--------------------------------------------");
		return ResponseSignatureDto.builder().signatureSeq(signatureSeq).build();
	}

	@Override
	public AuthenticationDto selectAuthentication(String did, String authenticationId) {
		 Optional<Did> clsDid = didRepository.findByDid(did);
		 log.info("clsDid.get().getDidSeq() {}", clsDid.get().getDidSeq());
		 Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(clsDid.get().getDidSeq());
		 
		 log.info("didDocument.get().getDidDocumentSeq() {}", didDocument.get().getDidDocumentSeq());
		 log.info("authenticationId {}", authenticationId);
		 
		 Optional<DidAuthentication> didAuthentication = didAuthenticationRepository.findByDidDocument_DidDocumentSeqAndAuthId(didDocument.get().getDidDocumentSeq(), authenticationId);
		 
		 AuthenticationDto authenticationDto = AuthenticationDto.builder()
				 .did(did)
				 .authId(didAuthentication.get().getAuthId())
				 .authType(didAuthentication.get().getAuthType())
				 .controller(didAuthentication.get().getController())
				 .publicKey(didAuthentication.get().getPublicKey())
				 .build();
		 
		return authenticationDto;
	}

	@Override
	public PublicKeyDto selectPublicKey(String did, String publicKeyId) {
		Optional<Did> clsDid = didRepository.findByDid(did);
		Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(clsDid.get().getDidSeq());
		Optional<DidPublicKey> didPublicKey = didPublicKeyRepository.findByDidDocument_DidDocumentSeqAndPkId(didDocument.get().getDidDocumentSeq(),publicKeyId);
		PublicKeyDto publicKeyDto = PublicKeyDto.builder()
				.did(did)
				.publicKeyId(didPublicKey.get().getPkId())
				.publicKey(didPublicKey.get().getPublicKey())
				.publicKeyType(didPublicKey.get().getPublicKeyType())
				.controller(didPublicKey.get().getController())
				.build();
		
		return publicKeyDto;
	}

	@Override
	public ServiceDto selectService(String did, String serviceId) {
		Optional<Did> clsDid = didRepository.findByDid(did);
		Optional<DidDocument> didDocument = didDocumentRepository.findByDid_DidSeq(clsDid.get().getDidSeq());
		Optional<DidService> didService = didServiceRepository.findByDidDocument_DidDocumentSeqAndServiceId(didDocument.get().getDidDocumentSeq(),serviceId);
		ServiceDto serviceDto = ServiceDto.builder()
				.did(did)
				.serviceId(didService.get().getServiceId())
				.serviceEndpoint(didService.get().getServiceEndpoint())
				.serviceType(didService.get().getServiceType())
				.publicKey(didService.get().getPublicKey())
				.build();		 
		return serviceDto;
	}

	@Override
	public SignatureDto selectSignature(DidSubDto didSubDto) {
		Optional<Signature> signature = signatureRepository.findByToDidAndSignSeq(didSubDto.getDid(), didSubDto.getVcSignatureSeq());
		if(signature.isPresent()) {
			SignatureDto signatureDto = SignatureDto.builder()
					.signature(signature.get().getSignature())
					.build();
			return signatureDto;
		}
		
		return null;
	}

}
