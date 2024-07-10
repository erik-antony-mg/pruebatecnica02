package com.amg.pruebatecnica02.infrastructure.persistence.specification;

import com.amg.pruebatecnica02.domain.entity.Phone;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SearchUsuarioSpecification implements Specification<Usuario> {

    private String email;
    private String numeroPhone;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Boolean isActive;

    @Override
    public Predicate toPredicate(@NonNull Root<Usuario> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {

        query.orderBy(criteriaBuilder.asc(root.get("email")));
        List<Predicate> predicates=new ArrayList<>();

        if (StringUtils.hasText(email)){
            Expression<String> emailToLowerCase=criteriaBuilder.lower(root.get("email"));
            Predicate nameLikePredicate =criteriaBuilder.like(emailToLowerCase,"%".concat(email).concat("%"));
            predicates.add(nameLikePredicate);
        }
        if (fechaDesde!=null){
            Predicate fechaDesdeGreaterThanEqualPredicate= criteriaBuilder
                    .greaterThanOrEqualTo(root.get("fechaCreacion"),fechaDesde);

            predicates.add(fechaDesdeGreaterThanEqualPredicate);
        }
        if (fechaHasta!=null){
            Predicate fechaHastaLessThanEqualsPredicate= criteriaBuilder
                    .lessThanOrEqualTo(root.get("fechaCreacion"),fechaHasta);

            predicates.add(fechaHastaLessThanEqualsPredicate);
        }
            Predicate isUserisActive= criteriaBuilder.equal(root.get("isActive"),isActive);
            predicates.add(isUserisActive);

        Join<Usuario, Phone> usuarioPhoneJoin = root.join("phones");
        if (StringUtils.hasText(numeroPhone)){

            Predicate phoneLikePredicate=criteriaBuilder.like(usuarioPhoneJoin.get("number"),"%".concat(numeroPhone).concat("%"));
            predicates.add(phoneLikePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
