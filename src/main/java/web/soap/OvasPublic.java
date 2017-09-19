package web.soap;

import com.sigetel.web.domain.OptionMap;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class OvasPublic {

    public String createRequest(@WebParam(name = "serviceCode", partName = "serviceCode") String serviceCode,
                                @WebParam(name = "requestedStatus", partName = "requestedStatus") String requestedStatus,
                                @WebParam(name = "anneedNumber", partName = "anneedNumber") String anneedNumber,
                                @WebParam(name = "clientNumber", partName = "clientNumber") String clientNumber,
                                @WebParam(name = "OptionMap", partName = "OptionMap") OptionMap optionMap) {
        return "Hello ";
    }
}


