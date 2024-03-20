package hello.proxy.blog;

import hello.proxy.blog.code.ClientObject;
import hello.proxy.blog.code.DataClient;
import hello.proxy.blog.code.DataInterface;
import hello.proxy.blog.code.DataInterfaceImpl;
import hello.proxy.blog.code.ServerInterface;
import hello.proxy.blog.code.ServerInterfaceImpl;
import hello.proxy.blog.code.proxy.CachingProxy;
import hello.proxy.blog.code.proxy.TimeServerProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServerClientTest {

    @Test
    @DisplayName("프록시 객체를 사용하지 않은 상태로 실행하기.")
    void no_proxy() {
        ServerInterface serverInterface = new ServerInterfaceImpl();
        ClientObject clientObject = new ClientObject(serverInterface);

        clientObject.execute();
    }

    @Test
    @DisplayName("시간 측정을 위한 프록시를 사용하여 실행하기.")
    void use_time_proxy() {
        ServerInterface serverInterface = new ServerInterfaceImpl();
        ServerInterface timeServerProxy = new TimeServerProxy(serverInterface);
        ClientObject clientObject = new ClientObject(timeServerProxy);

        clientObject.execute();
    }


    @Test
    @DisplayName("데이터를 조회하기 위해 구현체를 호출한다.")
    void no_proxy_data() {
        DataInterface dataInterface = new DataInterfaceImpl();
        DataClient dataClient = new DataClient(dataInterface);

        dataClient.execute();
        dataClient.execute();
        dataClient.execute();
    }

    @Test
    @DisplayName("캐싱 프록시 사용하기")
    void use_caching_proxy() {
        DataInterface dataInterface = new DataInterfaceImpl();
        DataInterface cachingProxy = new CachingProxy(dataInterface);
        DataClient dataClient = new DataClient(cachingProxy);

        dataClient.execute();
        dataClient.execute();
        dataClient.execute();
    }
}
