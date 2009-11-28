package nl.ddd;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see nl.ddd.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    private SpringComponentInjector springComponentInjector;


    /**
     * Constructor
     */
    public WicketApplication() {
    }


    @Override
    protected void init() {
        initSpring();

        String configurationType = getConfigurationType();

        System.out.println("ConfigurationType " + configurationType);
        if (DEVELOPMENT.equalsIgnoreCase(configurationType)) {
            getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        }

    }


    protected void initSpring() {
        //
        if (this.springComponentInjector == null) {
            this.springComponentInjector = new SpringComponentInjector(this);
        }
        //
        addComponentInstantiationListener(springComponentInjector);
        //
    }


    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}
