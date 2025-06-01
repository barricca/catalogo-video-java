package tech.wbrq.catalogo.domain;

import net.datafaker.Faker;
import tech.wbrq.catalogo.domain.category.Category;
import tech.wbrq.catalogo.domain.utils.IdUtils;
import tech.wbrq.catalogo.domain.utils.InstantUtils;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2020, 2030);
    }

    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "System Design no Mercado Livre na prática",
                "Não cometa esses erros ao trabalhar com Microsserviços",
                "Testes de Mutação. Você não testa seu software corretamente"
        );
    }

    public static String checksum() {
        return "03fe62de";
    }

    public static final class Categories {

        public static Category aulas() {
            return Category.with(
                    IdUtils.uuid(),
                    "Aulas",
                    "Aulas de programação e tecnologia - gravadas",
                    true,
                    InstantUtils.now(),
                    InstantUtils.now(),
                    null
            );
        }

        public static Category lives() {
            return Category.with(
                    IdUtils.uuid(),
                    "Lives",
                    "Aulas de programação e tecnologia - ao vivo",
                    true,
                    InstantUtils.now(),
                    InstantUtils.now(),
                    null
            );
        }
    }
}
