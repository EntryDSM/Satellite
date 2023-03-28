package kr.hs.entrydsm.satellite.common.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.regex.Pattern

internal class RegexUtilTest : DescribeSpec({

    describe("NUMBER_EXP") {
        context("숫자로 된 String이 주어지면") {
            val string = "1234567890"
            it("참이 반환된다") {
                Pattern.matches(RegexUtil.NUMBER_EXP, string) shouldBe true
            }
        }
        context("문자가 포함된 String이 주어지면") {
            val string = "1234567abc"
            it("거짓이 반환된다") {
                Pattern.matches(RegexUtil.NUMBER_EXP, string) shouldBe false
            }
        }
        context("공백이 포함된 String이 주어지면") {
            val string = "123 567890"
            it("거짓이 반환된다") {
                Pattern.matches(RegexUtil.NUMBER_EXP, string) shouldBe false
            }
        }
    }

    describe("PASSWORD_EXP") {
        context("영어 대소문자, 영어, 특수문자가 포함된 8-30자의 문자열이 주어지면") {
            val string = "Password!1"
            it("참이 반환된다") {
                Pattern.matches(RegexUtil.PASSWORD_EXP, string) shouldBe true
            }
        }
        context("8자 이하의 문자열이 주어지면") {
            val string = "Pw!1"
            it("거짓이 반환된다") {
                Pattern.matches(RegexUtil.PASSWORD_EXP, string) shouldBe false
            }
        }
        context("30자 이상의 문자열이 주어지면") {
            val string = "123456789012345678901234567890aA!1"
            it("거짓이 반환된다") {
                Pattern.matches(RegexUtil.PASSWORD_EXP, string) shouldBe false
            }
        }
    }
})