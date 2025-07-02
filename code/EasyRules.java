public class EasyRules {
    private Rules reglas;
    private RulesEngine engine;

    public EasyRules(Servicio servicio) {
        engine = new DefaultRulesEngine();
        reglas = new Rules();

        reglas.register(new RuleBuilder().name("conyuge")
                .when(facts -> ((Afiliacion) facts.get("afiliado"))
                        .getCategoria() == Categoria.FAMILIAR
                        && ((Afiliacion) facts.get("afiliado"))
                                .getSubcategoria() == Subcategoria.CONYUGE)
                .then(facts -> facts.put("monto",
                        calcularCuota(servicio.getConyuge(
                                ((Afiliacion) facts.get("afiliado")).getId()))
                                * 0.7))
                .build());
        reglas.register(new RuleBuilder().name("descendiente de primer grado")
                .when(facts -> ((Afiliacion) facts.get("afiliado"))
                        .getCategoria() == Categoria.FAMILIAR
                        && ((Afiliacion) facts.get("afiliado"))
                                .getSubcategoria() == Subcategoria.DESCENDIENTE_PRIMER_GRADO)
                .then(facts -> facts.put("monto", 0.0)).build());
        reglas.register(new RuleBuilder().name("becario")
                .when(facts -> ((Afiliacion) facts.get("afiliado"))
                        .getCategoria() == Categoria.VOLUNTARIO_ADHERENTE
                        && ((Afiliacion) facts.get("afiliado"))
                                .getSubcategoria() == Subcategoria.BECARIO)
                .then(facts -> {
                    Afiliacion afiliado = ((Afiliacion) facts.get("afiliado"));
                    double monto;

                    if (afiliado.getEdad() <= 30) {
                        monto = servicio.getCMMU() * 0.45;
                    } else if (afiliado.getEdad() <= 45) {
                        monto = servicio.getCMMU() * 0.82;
                    } else if (afiliado.getEdad() <= 55) {
                        monto = servicio.getCMMU();
                    } else if (afiliado.getEdad() <= 65) {
                        monto = servicio.getCMMU() * 1.2;
                    } else {
                        monto = servicio.getCMMU() * 2;
                    }
                    facts.put("monto", monto);
                }).build());
        reglas.register(new RuleBuilder().name("agente UNSL con licencia")
                .when(facts -> ((Afiliacion) facts.get("afiliado"))
                        .getCategoria() == Categoria.VOLUNTARIO_ADHERENTE
                        && ((Afiliacion) facts.get("afiliado"))
                                .getSubcategoria() == Subcategoria.AGENTE_UNSL_CON_LICENCIA)
                .then(facts -> {
                    Afiliacion afiliado = ((Afiliacion) facts.get("afiliado"));
                    double monto;

                    if (afiliado.getEdad() <= 30) {
                        monto = servicio.getHaberPercibido(afiliado.getId())
                                * 0.4;
                    } else if (afiliado.getEdad() <= 45) {
                        monto = servicio.getHaberPercibido(afiliado.getId())
                                * 0.78;
                    } else if (afiliado.getEdad() <= 55) {
                        monto = servicio.getHaberPercibido(afiliado.getId())
                                * 0.95;
                    } else if (afiliado.getEdad() <= 65) {
                        monto = servicio.getHaberPercibido(afiliado.getId())
                                * 1.13;
                    } else {
                        monto = servicio.getHaberPercibido(afiliado.getId())
                                * 1.86;
                    }
                    facts.put("monto", monto);
                }).build());
    }

    public double calcularCuota(Afiliacion afiliado) {
        Facts facts = new Facts();
        facts.put("afiliado", afiliado);
        engine.fire(reglas, facts);
        double monto = facts.get("monto") == null ? 0 : facts.get("monto");

        return monto;
    }
}
