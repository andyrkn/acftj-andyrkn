package interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Interceptor
@Log
public class LoggingInterceptor implements Serializable {

    private Logger logger = Logger.getAnonymousLogger();

    @AroundInvoke
    public Object logMethod(InvocationContext ctx) throws Exception {
        logger.info("Method called:" + ctx.getMethod().getName() +" with parameters:"+ Arrays.stream(ctx.getMethod().getParameterTypes()).map(Class::getName).collect(Collectors.joining(",")));
        return ctx.proceed();
    }
}

