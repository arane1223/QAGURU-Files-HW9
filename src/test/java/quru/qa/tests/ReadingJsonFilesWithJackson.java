package quru.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quru.qa.model.Simple;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест на чтение данных из json файла библиотекой Jackson")
public class ReadingJsonFilesWithJackson {

    @DisplayName("Успешное чтение данных из json")
    @Test
    void jsonFileTest () throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/simple.json");
        Simple jsonObject = objectMapper.readValue(file, Simple.class);
        assertThat(jsonObject.name).isEqualTo("Sergey");
        assertThat(jsonObject.surname).isEqualTo("Glukhov");
        assertThat(jsonObject.music).hasSize(2);
        assertThat(jsonObject.music.get(0)).isEqualTo("Black Metal");
        assertThat(jsonObject.music.get(1)).isEqualTo("Death metal");
        assertThat(jsonObject.address.street).isEqualTo("Sheinkmana");
        assertThat(jsonObject.address.house).isEqualTo(19);
    }
}
