package poussecafe.journal;

import poussecafe.discovery.BoundedContextConfigurer;

public class Journal {

    private Journal() {

    }

    public static BoundedContextConfigurer configure() {
        return new BoundedContextConfigurer.Builder()
                .packagePrefix("poussecafe.journal")
                .packagePrefix("poussecafe.support")
                .build();
    }
}
