package com.napier.group21;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    void main_unsuccessfulConnection() {
        App.main(new String[]{"hostNameWhichDoesNotExist:12345"});
    }
}
