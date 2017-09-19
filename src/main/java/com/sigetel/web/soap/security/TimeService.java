package com.sigetel.web.soap.security;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by mumarm45 on 09/08/2017.
 */
@Service
public class TimeService {

    Date now(){
        return  new Date();
    }
}
