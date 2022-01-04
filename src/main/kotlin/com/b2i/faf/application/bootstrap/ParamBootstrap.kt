package com.b2i.faf.application.bootstrap

import com.b2i.faf.domain.account.entity.Civility
import com.b2i.faf.domain.account.entity.Period
import com.b2i.faf.domain.account.entity.Sex
import com.b2i.faf.domain.account.entity.StateData
import com.b2i.faf.domain.account.port.ParamDomain
import com.b2i.faf.domain.account.port.PeriodDomain

object ParamBootstrap {


    fun seed(
            paramDomain: ParamDomain,
            periodDomain: PeriodDomain,
    ) {

        if (paramDomain.countCivility() == 0L) {
            var civility = Civility()

            //item1
            civility.label = "Madame"
            civility.description = "Madame"
            civility.state = StateData.ENABLED
            paramDomain.saveCivility(civility)

            //item2
            civility.label = "Mademoiselle"
            civility.description = "Madame"
            civility.state = StateData.ENABLED
            paramDomain.saveCivility(civility)

            //item3
            civility.label = "Monsieur"
            civility.description = "Monsieur"
            civility.state = StateData.ENABLED
            paramDomain.saveCivility(civility)
        }

        if (paramDomain.countSex() == 0L) {
            var sex = Sex()

            //item1
            sex.label = "Masculin"
            sex.description = "Masculin"
            sex.state = StateData.ENABLED
            paramDomain.saveSex(sex)

            //item2
            sex.label = "Féminin"
            sex.description = "Masculin"
            sex.state = StateData.ENABLED
            paramDomain.saveSex(sex)
        }

        if (periodDomain.countPeriod() == 0L) {
            var period = Period()

            period.label = "Matin"
            period.description = "Matin"
            period.state = StateData.ENABLED
            periodDomain.savePeriod(period)

            period.label = "Après-midi"
            period.description = "Après-midi"
            period.state = StateData.ENABLED
            periodDomain.savePeriod(period)
        }


    }

}