package kr.co.rur.storage.db.core.config.jpa

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Service
import java.util.*

@Service
class AwareAuditService : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        return Optional.empty()
    }
}