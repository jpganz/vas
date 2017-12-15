package com.sigetel.web.service.validator;

import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.domain.RequestParameter;
import com.sigetel.web.domain.RequestParams;
import com.sigetel.web.service.ProviderCommandService;
import com.sigetel.web.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EntryValidator {


    @Autowired
    ProviderService providerService;

    @Autowired
    ProviderCommandService providerCommandService;

    @Transactional
    public Long isValidEntry(RequestParams requestParams) {

        Map<String, String> params = requestParams.getOptionMap();
        if (params.size() == 0) {
            return 0l;
        }
        List<String> sentParameterNames = new ArrayList<String>();

        for (String key : params.keySet()) {
            sentParameterNames.add(key);
        }

        List<ProviderCommand> commands = providerCommandService.findByProviderCodeAndCommandName(requestParams.getRequestedStatus(), requestParams.getServiceCode());
        if (commands.size() == 0) {
            return 0l;
        }
        ProviderCommand selectedCommand = null;
        for (ProviderCommand command : commands) {
            List<String> paramNames = new ArrayList<String>();
            int requiredFields = 0;
            int nonRequiredFields = 0;
            for (RequestParameter request : command.getRequestParameters()) {
                paramNames.add(request.getName());
                if (request.isIsMandatory()) {
                    requiredFields++;
                } else {
                    nonRequiredFields++;
                }
            }
            if((paramNames).containsAll(sentParameterNames)){
                selectedCommand = command;
            }
            if(selectedCommand != null && selectedCommand.getId() != null){
                return selectedCommand.getId();
            }
            return null;
        }

        return null;
    }
}
