public class Rulebook {
    RuleBook<Double> ruleBook;

    public Rulebook(Servicio servicio) {
        ruleBook = RuleBookBuilder.create().withResultType(Double.class)
                .withDefaultResult(0.0)
                .addRule(rule -> rule.withFactType(Afiliacion.class)
                        .when(facts -> facts.getOne()
                                .getCategoria() == Categoria.FAMILIAR
                                && facts.getOne()
                                        .getSubcategoria() == Subcategoria.CONYUGE)
                        .then((facts, result) -> {
                            result.setValue(calcularCuota(
                                    servicio.getConyuge(facts.getOne().getId()))
                                    * 0.7);
                        }).stop())
                .addRule(rule -> rule.withFactType(Afiliacion.class)
                        .when(facts -> facts.getOne()
                                .getCategoria() == Categoria.FAMILIAR
                                && facts.getOne()
                                        .getSubcategoria() == Subcategoria.DESCENDIENTE_PRIMER_GRADO)
                        .then((facts, result) -> {
                            result.setValue(0.0);
                        }).stop())
                .addRule(rule -> rule.withFactType(Afiliacion.class)
                        .when(facts -> facts.getOne()
                                .getCategoria() == Categoria.VOLUNTARIO_ADHERENTE
                                && facts.getOne()
                                        .getSubcategoria() == Subcategoria.BECARIO)
                        .then((facts, result) -> {
                            Afiliacion afiliado = facts.getOne();
                            if (afiliado.getEdad() <= 30) {
                                result.setValue(servicio.getCMMU() * 0.45);
                            } else if (afiliado.getEdad() <= 45) {
                                result.setValue(servicio.getCMMU() * 0.82);
                            } else if (afiliado.getEdad() <= 55) {
                                result.setValue(servicio.getCMMU());
                            } else if (afiliado.getEdad() <= 65) {
                                result.setValue(servicio.getCMMU() * 1.2);
                            } else {
                                result.setValue(servicio.getCMMU() * 2);
                            }
                        }).stop())
                .addRule(rule -> rule.withFactType(Afiliacion.class)
                        .when(facts -> facts.getOne()
                                .getCategoria() == Categoria.VOLUNTARIO_ADHERENTE
                                && facts.getOne()
                                        .getSubcategoria() == Subcategoria.AGENTE_UNSL_CON_LICENCIA)
                        .then((facts, result) -> {
                            Afiliacion afiliado = facts.getOne();
                            if (afiliado.getEdad() <= 30) {
                                result.setValue(servicio.getHaberPercibido(
                                        afiliado.getId()) * 0.4);
                            } else if (afiliado.getEdad() <= 45) {
                                result.setValue(servicio.getHaberPercibido(
                                        afiliado.getId()) * 0.78);
                            } else if (afiliado.getEdad() <= 55) {
                                result.setValue(servicio.getHaberPercibido(
                                        afiliado.getId()) * 0.95);
                            } else if (afiliado.getEdad() <= 65) {
                                result.setValue(servicio.getHaberPercibido(
                                        afiliado.getId()) * 1.13);
                            } else {
                                result.setValue(servicio.getHaberPercibido(
                                        afiliado.getId()) * 1.86);
                            }
                        }).stop())
                .build();
    }

    public double calcularCuota(Afiliacion afiliado) {
        FactMap<Afiliacion> facts = new FactMap<>();
        facts.setValue("", afiliado);
        ruleBook.run(facts);

        return ruleBook.getResult().get().getValue();
    }
}
