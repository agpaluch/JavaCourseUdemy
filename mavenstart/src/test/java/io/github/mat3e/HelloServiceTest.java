package io.github.mat3e;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {

    private final static String WELCOME = "Hello";
    private final static String FALLBACK_ID_WELCOME = "Hola";

    @Test
    public void testPrepareGreetingNullNameReturnsGreetingWithFallbackName() throws Exception {
        //given
        var mockRepository = alwaysReturnHelloRepository();
        var SUT = new HelloService(mockRepository); //SUT - system under test

        //when
        var result = SUT.prepareGreeting(null, "-1");

        //then
        assertEquals(WELCOME + " " +HelloService.FALLBACK_NAME+"!", result);
    }


    @Test
    public void testPrepareGreetingNameReturnsGreetingWithName() throws Exception {
        //given
        var SUT = new HelloService();
        var name = "test";

        // when
        var result = SUT.prepareGreeting(name, "-1");

        //then
        assertEquals(WELCOME + " " +name+"!", result);
    }


    @Test
    public void testPrepareGreetingNullLangReturnsGreetingWithFallbackIdLang() throws Exception {
        //given
        LangRepository mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository); //SUT - system under test

        //when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " +HelloService.FALLBACK_NAME+"!", result);

    }


    @Test
    public void testPrepareGreetingTextLangReturnsGreetingWithFallbackIdLang() throws Exception {

        //given
        LangRepository mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository); //SUT - system under test

        //when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " +HelloService.FALLBACK_NAME+"!", result);

    }



    @Test
    public void testPrepareGreetingNonExistingLangReturnsGreetingWithFallbackIdLang() throws Exception {

        //given
        LangRepository mockRepository = new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository); //SUT - system under test

        //when
        var result = SUT.prepareGreeting(null, "-1");

        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg()+" "+HelloService.FALLBACK_NAME+"!", result);

    }



    private LangRepository fallbackLangIdRepository() {
        return new LangRepository(){

                @Override
                Optional<Lang> findById(Integer id) {
                    if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                        return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));

                    } else {
                        return Optional.empty();
                    }
                }
            };
    }


    private LangRepository alwaysReturnHelloRepository() {
        return new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }
}
