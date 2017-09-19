package com.sigetel.web.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.sigetel.web.wsdl.client.AuthService;
import com.sigetel.web.wsdl.client.AuthenticationServiceSoapBindingQSService;
import com.sigetel.web.wsdl.client.UserInformationResponse;
import com.sigetel.web.wsdl.handler.WSSecurityHeaderSOAPHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AuthTigoApp{

	private String wsAuthUser;
	private String wsAuthPassword;

	public AuthTigoApp() {

	}

	public void setCredentialsWS(String user, String pass) {
		setWsAuthUser(user);
		setWsAuthPassword(pass);
	}

	public UserInformationResponse requestUserInfo(int ldapId, String userName, String userPassword) {

		AuthenticationServiceSoapBindingQSService service = new AuthenticationServiceSoapBindingQSService();
    	AuthService myServicePort = service.getAuthenticationServiceSoapBindingQSPort();

        BindingProvider bindingProvider = (BindingProvider) myServicePort;
        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(new WSSecurityHeaderSOAPHandler(getWsAuthUser(), getWsAuthPassword()));
        bindingProvider.getBinding().setHandlerChain(handlerChain);

        UserInformationResponse userInformationResponse =
        		myServicePort.requestUserInfo(ldapId, userName, userPassword);

        return userInformationResponse;
	}

	public JSONObject processUserInformationResponse(UserInformationResponse userInformationResponse) {

		JSONObject response = new JSONObject();
		JSONObject userInfo = new JSONObject();
		JSONArray listInfo = new JSONArray();

		if(!userInformationResponse.getErrorMessage().equals("success")) {

			response.put("errorMessage", userInformationResponse.getErrorMessage());

			return response;
		}

		userInfo.put("alias",userInformationResponse.getAlias());
		userInfo.put("email",userInformationResponse.getEmail());

		listInfo.add(userInfo);

		response.put("userInformation", listInfo);

		return response;
	}

	public String getWsAuthUser() {
		return wsAuthUser;
	}

	public void setWsAuthUser(String wsAuthUser) {
		this.wsAuthUser = wsAuthUser;
	}

	public String getWsAuthPassword() {
		return wsAuthPassword;
	}

	public void setWsAuthPassword(String wsAuthPassword) {
		this.wsAuthPassword = wsAuthPassword;
	}
}
