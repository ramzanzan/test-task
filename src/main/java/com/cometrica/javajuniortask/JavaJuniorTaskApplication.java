package com.cometrica.javajuniortask;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;


@SpringBootApplication
public class JavaJuniorTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaJuniorTaskApplication.class, args);
	}

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("junior-task:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}


//todo lock debt while adding payment
//todo (cID,dID,pID)+simpleQuery || (dID,pID)+complexQuery