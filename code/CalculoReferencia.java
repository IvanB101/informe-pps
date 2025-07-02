public class Base {
    Servicio servicio;

    public Base(Servicio servicio) {
        this.servicio = servicio;
    }

    public double calcularCuota(Afiliacion afiliado) {
        if (afiliado.getCategoria() == Categoria.FAMILIAR) {
            if (afiliado.getSubcategoria() == Subcategoria.CONYUGE) {
                return calcularCuota(servicio.getConyuge(afiliado.getId()))
                        * 0.7;
            }
            if (afiliado
                    .getSubcategoria() == Subcategoria.DESCENDIENTE_PRIMER_GRADO) {
                return 0;
            }
        }
        if (afiliado.getCategoria() == Categoria.VOLUNTARIO_ADHERENTE) {
            if (afiliado.getSubcategoria() == Subcategoria.BECARIO) {
                if (afiliado.getEdad() <= 30) {
                    return servicio.getCMMU() * 0.45;
                } else if (afiliado.getEdad() <= 45) {
                    return servicio.getCMMU() * 0.82;
                } else if (afiliado.getEdad() <= 55) {
                    return servicio.getCMMU();
                } else if (afiliado.getEdad() <= 65) {
                    return servicio.getCMMU() * 1.2;
                } else {
                    return servicio.getCMMU() * 2;
                }
            }
        }
        if (afiliado.getCategoria() == Categoria.VOLUNTARIO_ADHERENTE) {
            if (afiliado
                    .getSubcategoria() == Subcategoria.AGENTE_UNSL_CON_LICENCIA) {
                if (afiliado.getEdad() <= 30) {
                    return servicio.getHaberPercibido(afiliado.getId()) * 0.4;
                } else if (afiliado.getEdad() <= 45) {
                    return servicio.getHaberPercibido(afiliado.getId()) * 0.78;
                } else if (afiliado.getEdad() <= 55) {
                    return servicio.getHaberPercibido(afiliado.getId()) * 0.95;
                } else if (afiliado.getEdad() <= 65) {
                    return servicio.getHaberPercibido(afiliado.getId()) * 1.13;
                } else {
                    return servicio.getHaberPercibido(afiliado.getId()) * 1.86;
                }
            }
        }

        throw new RuntimeException("Subcategoria inválida para categoría");
    }
}
