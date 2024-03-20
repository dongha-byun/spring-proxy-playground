package hello.proxy.blog.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataInterfaceImpl implements DataInterface{

    @Override
    public String getData() {
        log.info("DataInterfaceImpl getData() call");
        return "data";
    }
}
