package com.deploy.aws.services.impl;

import com.deploy.aws.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevDataServiceImpl implements DataService {

    @Override
    public String getData() {
        return "Dev Data...";
    }
}
