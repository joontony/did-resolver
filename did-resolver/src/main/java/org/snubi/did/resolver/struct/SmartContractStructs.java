package org.snubi.did.resolver.struct;

import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;

public class SmartContractStructs  {	
	public static class Authentication extends DynamicStruct {
		public String id;
		public String authType;
		public String controller;
		public String publicKey;
		public Authentication(String id, String authType, String controller, String publicKey) {
		    super(new Utf8String(id), new Utf8String(authType), new Utf8String(controller), new Utf8String(publicKey));
		    this.id = id;
		    this.authType = authType;
		    this.controller = controller;
		    this.publicKey = publicKey;
		}
		public Authentication(Utf8String id, Utf8String authType, Utf8String controller, Utf8String publicKey) {
		    super(id, authType,controller,publicKey);
		    this.id = id.getValue();
		    this.authType = authType.getValue();
		    this.controller = controller.getValue();
		    this.publicKey = publicKey.getValue();
		}
	}	
	public static class PublicKey extends DynamicStruct {
		public String id;
		public String PublicKeyType;
		public String controller;
		public String publicKey;
		public PublicKey(String id, String PublicKeyType, String controller, String publicKey) {
		    super(new Utf8String(id), new Utf8String(PublicKeyType), new Utf8String(controller), new Utf8String(publicKey));
		    this.id = id;
		    this.PublicKeyType = PublicKeyType;
		    this.controller = controller;
		    this.publicKey = publicKey;
		}
		public PublicKey(Utf8String id, Utf8String PublicKeyType, Utf8String controller, Utf8String publicKey) {
		    super(id, PublicKeyType,controller,publicKey);
		    this.id = id.getValue();
		    this.PublicKeyType = PublicKeyType.getValue();
		    this.controller = controller.getValue();
		    this.publicKey = publicKey.getValue();
		}
	}	
	public static class Service extends DynamicStruct {
		public String id;
		public String ServiceType;
		public String publicKey;
		public String serviceEndpoint;
		public Service(String id, String ServiceType, String publicKey, String serviceEndpoint) {
		    super(new Utf8String(id), new Utf8String(ServiceType), new Utf8String(publicKey), new Utf8String(serviceEndpoint));
		    this.id = id;
		    this.ServiceType = ServiceType;
		    this.publicKey = publicKey;
		    this.serviceEndpoint = serviceEndpoint;
		}
		public Service(Utf8String id, Utf8String ServiceType, Utf8String publicKey, Utf8String serviceEndpoint) {
		    super(id, ServiceType,publicKey,serviceEndpoint);
		    this.id = id.getValue();
		    this.ServiceType = ServiceType.getValue();
		    this.publicKey = publicKey.getValue();
		    this.serviceEndpoint = serviceEndpoint.getValue();
		}
	}	
}