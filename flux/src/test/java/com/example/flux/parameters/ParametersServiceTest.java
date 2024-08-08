package com.example.flux.parameters;

import com.example.flux.parameters.model.ParametersEntity;
import com.example.flux.parameters.repository.ParametersRepository;
import com.example.flux.parameters.service.ParametersService;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParametersServiceTest {

    @InjectMocks
    private ParametersService parametersService;

    @Mock
    private JwtService jwtService;

    @Mock
    private ParametersRepository parametersRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testUpdateParameterValue_ValidRoleAndValue()
            throws NoGrantedAuthorityException {
        // Initialize dummy data
        String token = "valid-token";
        ParametersEntity parametersEntity = new ParametersEntity();
        parametersEntity.setValue("10");

        // Mocking
        doNothing().when(jwtService).checkRole(token, Roles.ROLE_ADMIN);

        // Method call
        parametersService.updateParameterValue(token, parametersEntity);

        // Verification
        verify(jwtService, times(1)).checkRole(token, Roles.ROLE_ADMIN);
        verify(parametersRepository, times(1)).save(parametersEntity);
    }

    @Test(expected = Exception.class)
    public void testUpdateParameterValue_InvalidValue() throws Exception {
        String token = "valid-token";
        ParametersEntity parametersEntity = new ParametersEntity();
        parametersEntity.setValue("something"); // Assuming invalid value

        // Mocking

        doNothing().when(jwtService).checkRole(token, Roles.ROLE_ADMIN);

        // Method call
        parametersService.updateParameterValue(token, parametersEntity);

        // Verification
        verify(jwtService, times(1)).checkRole(token, Roles.ROLE_ADMIN);
        verify(parametersRepository, never()).save(any(ParametersEntity.class));
    }
}

