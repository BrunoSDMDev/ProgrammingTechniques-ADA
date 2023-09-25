package Aula2;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * Assunto abordado: Java Time, trabalhando com datas e horas
 * Obs: evitaremos a utilização do Java <1.8 por conta do pacote java.time
 */
public class Classroom {

    public static void main(String[] args) {
        System.out.println("Boa noite!");
        showDataHora();
        showParser();
        showOperations();
        showChallengeOne();
        showZonedDateTime();
        showTemporalAdjusters();
        showPeriodDurationAndInstant();
        showChallengeTwo();
        showDaylightSavings();
    }
    /**
     * LocalDate (representa o dia), LocalTime (representa a hora), LocalDateTime (representa o dia e hora)
     */
    private static void showDataHora() {
        // método now() -> retorna a data/hora atual do relógio do sistema host
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        // método of() -> retorna a data/hora informada
        System.out.println(LocalDate.of(2023, Month.SEPTEMBER, 22));
        System.out.println(LocalTime.of(10,7, 40));

    }

    /**
     * Utiliza o método parse() para converter Strings em objetos de data e/ou hora.
     * LocalDate.parse("YYYY-MM-DD")
     * LocalTime.parse("hh:mm:ss")
     * LocalDateTime.parse("YYYY-MM-DDThh:mm:ss")
     *
     * Utiliza o método DateTimeFormatter.ofPattern() para sobrescrever o formato de data/hora.
     * Obs: para um objeto de data/hora já instanciado, utilizar o método format().
     */
    private static void showParser() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); // dia precisa ser indicado com 'dd'
        System.out.println(now.format(formatter));

        LocalDate date1 = LocalDate.parse("2002-09-22");
        System.out.println(date1);
    }

    /**
     * EXEMPLOS:
     * getDayOfWeek() -> retorna ENUM (DayOfWeek) representando dia da semana
     * getEra() -> retorna ENUM (IsoEra) BCE (Before Christ Era) ou CE (Christ Era, vulgo AD ou Anno Domini)
     * minusDays() -> subtrai dias
     * minusDays() -> incrementa dias
     */
    private static void showOperations() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println("Dia da Semana: " + date.getDayOfWeek());
        System.out.println("Adicionar horas: " + time.plusHours(2));
        System.out.println(date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));

    }

    /**
     * Challenge One: use o stdout para exibir o dia da semana anterior ao dia do seu nascimento.
     */
    private static void showChallengeOne() {
        LocalDate dataAnteriorAoMeuNascimento = LocalDate.of(2002, 9, 21);
        LocalDate diaAnteriorAoMeuNascimento =  LocalDate.of(2002, 9, 21).minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        System.out.println("Data anterior ao meu nascimento: " + dataAnteriorAoMeuNascimento.format(formatter));

        System.out.println("Dia anterior ao meu nascimento: " +
                (diaAnteriorAoMeuNascimento.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))));
    }

    /**
     * GMT (Greenwich Mean Time) e o UTC (Universal Time Coordinated) representam o mesmo horário.
     * Cada fuso é presentado como uma operação desse horário universal, exemplo: horário de Brasília == GMT-3
     *
     * Utiliza o ZonedDateTime.of(LocalDateTime, ZoneId) para converter a data e hora para um fuso específico.
     *
     * O ZoneId.of() pode ser utilizado para obter um ZoneId à partir de uma String (precisa ser específica).
     * Obs: consultar a documentação do ZoneId para saber os fusos disponíveis.
     */
    private static void showZonedDateTime() {

        // Para calcular o fuso, utilizar o now(ZoneId)
        ZonedDateTime tokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println("Horário no Japão: " + tokyo);

        // Para instanciar um horário específico de umm fuso, utilizar o of (LocalDateTime, ZoneId)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
        System.out.println("Horário no Japão: " + formatter.format(tokyo));

    }

    /**
     * Utiliza a classe TemporalAdjusters para realizar operações com a data/hora já instanciada.
     *
     * EXEMPLO:
     * LocalDateTime.with(TemporalAdjusters.next(DayOfWeek)) -> retorna uma cópia do objeto original alterando a data
     * para a próxima sexta-feira.
     */
    private static void showTemporalAdjusters() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Agora: " + now);
        System.out.println("Ultimo dia do mês: " + now.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("Próximo dia do mês: " + now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
    }

    /**
     * Period -> representa dias, meses ou anos
     * Duration -> representa minutos ou horas
     * Instant -> representa segundos e nano-segundos (Unix Timestamp, também conhecido como Epoch, desde 01/01/1970)
     *
     * Obs: podemos combinar as operações anteriores, como plus(), com as classes de período, duração e instante.
     */
    private static void showPeriodDurationAndInstant() {
        Period period = Period.between(LocalDate.of(2020, 1, 1), LocalDate.now());
        System.out.println("Período passado de 2020 até hoje (em meses): " + period.toTotalMonths());

        Period twoDays = Period.ofDays(2);
        System.out.println("Dois dias depois: " + LocalDate.now().plus(twoDays));

        long result = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.of(23,10,54));
        System.out.println("Minutes between: " + result);
    }
    /**
     * Utiliza o método ZoneId.getRules().isDaylightSavings(ZonedDateTime.toInstant()) para verificar se o horário
     * foi alterado devido ao horário de verão.
     */
    private static void showDaylightSavings() {
        ZoneId sp = ZoneId.of("America/Sao_Paulo");
        boolean isDayLightSavings = sp.getRules().isDaylightSavings(ZonedDateTime.of(LocalDate.of(2019, 1,1 ), LocalTime.now(), sp).toInstant());

        System.out.println("Essa data estava em horário de verão? " + isDayLightSavings);

    }

    /**
     * Challenge Two: use o stdout para imprimir a duração de execução do método abaixo.
     */
    private static void showChallengeTwo() {
        Instant inicioExecucao = Instant.now();
        for (int i = 0; i < 100; i++) {
            System.out.println("Execução de número: " + i);
        }
        Instant fimExecucao = Instant.now();

        Duration duration = Duration.between(inicioExecucao, fimExecucao);
        System.out.println("A execução do loop levou: " + duration.toMillis() + " milissegundos");
    }


    /**
     * Utiliza o método ZonedDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle).withLocale(Locale))
     * para converter uma data e hora para uma linguagem/cultura diferente.
     */
    private static void showLocaleDateTime() {
        Locale br = new Locale("pt", "BR");
        Locale jp = new Locale("ja");
        Locale us = new Locale("en", "US");

        ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(br));
    }
}
