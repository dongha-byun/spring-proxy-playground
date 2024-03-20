package hello.proxy.blog.code.proxy;

import hello.proxy.blog.code.DataInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CachingProxy implements DataInterface {
    private final DataInterface dataInterface;
    private String cachingValue;

    @Override
    public String getData() {
        log.info("CachingProxy call");
        if(cachingValue == null) {
            cachingValue = dataInterface.getData();
        }
        return cachingValue;
    }
}
