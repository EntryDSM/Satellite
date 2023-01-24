package kr.hs.entrydsm.exit.global.config.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.querydsl.QuerydslPredicateExecutor

fun <T> QuerydslPredicateExecutor<T>.findBy(vararg expressions: BooleanExpression?): List<T> {

    var predicate = expressions[0]!!
    expressions.forEach { predicate = predicate.and(it) }

    return findAll(predicate).toList()
}