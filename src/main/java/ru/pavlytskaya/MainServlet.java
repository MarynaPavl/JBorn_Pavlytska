package ru.pavlytskaya;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import ru.pavlytskaya.controller.AuthController;
import ru.pavlytskaya.controller.Controller;
import ru.pavlytskaya.controller.RegistrationController;
import ru.pavlytskaya.controller.SecureController;
import ru.pavlytskaya.api.json.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private ObjectMapper om = new ObjectMapper();
    private Map<String, Controller> controllers = new HashMap<>();
    private Map<String, SecureController> secureControllers = new HashMap<>();

    public MainServlet() {
        ApplicationContext context = SpringContext.getContext();

        for (String beanName : context.getBeanNamesForType(Controller.class)) {
            controllers.put(beanName, context.getBean(beanName, Controller.class));
        }
        for (String beanName : context.getBeanNamesForType(SecureController.class)) {
            secureControllers.put(beanName, context.getBean(beanName, SecureController.class));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uri = req.getRequestURI();

        res.setContentType("aplication/json");

        try {
            Controller controller = controllers.get(uri);
            if (controller != null) {
                if (controller instanceof AuthController) {
                    AuthController authController = (AuthController) controller;

                    AuthRequest authRequest = om.readValue(req.getInputStream(), authController.getRequestClass());
                    AuthResponse authResponse = authController.handler(authRequest);

                    if (authResponse == null) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        HttpSession session = req.getSession();
                        session.setAttribute("userId", authResponse.getId());

                        om.writeValue(res.getWriter(), authResponse);
                    }
                }
                if (controller instanceof RegistrationController) {
                    RegistrationController registrationController = (RegistrationController) controller;

                    RegistrationRequest registrationRequest = om.readValue(req.getInputStream(), registrationController.getRequestClass());
                    RegistrationResponse registrationResponse = registrationController.handler(registrationRequest);

                    if (registrationResponse == null) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        HttpSession session = req.getSession();
                        session.setAttribute("userId", registrationResponse.getUserId());

                        om.writeValue(res.getWriter(), registrationResponse);
                    }
                } else {
                    om.writeValue(
                            res.getWriter(),
                            controller.handler(om.readValue(req.getInputStream(), controller.getRequestClass()))
                    );
                }
            } else {
                SecureController secureController = secureControllers.get(uri);
                if (secureController != null) {
                    HttpSession session = req.getSession();
                    Long userId = (Long) session.getAttribute("userId");
                    if (userId == null) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        om.writeValue(
                                res.getWriter(),
                                secureController.handler(
                                        om.readValue(req.getInputStream(), secureController.getRequestClass()),
                                        userId
                                )
                        );
                    }
                } else {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            om.writeValue(res.getWriter(), new ErrorResponse(e.getMessage()));
        }
    }
}