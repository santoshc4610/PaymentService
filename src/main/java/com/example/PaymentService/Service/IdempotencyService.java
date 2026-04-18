package com.example.PaymentService.Service;


import com.example.PaymentService.Model.IdempotencyModel;
import com.example.PaymentService.Repository.Idempotency;
import com.example.PaymentService.Repository.PaymentRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class IdempotencyService {

    private final Idempotency idempotencyRepo;
    @PersistenceContext
    private EntityManager em;



    public IdempotencyService(Idempotency idempotencyRepo)
    {
        this.idempotencyRepo = idempotencyRepo;
    }
    @Transactional(propagation = REQUIRES_NEW, noRollbackFor = Exception.class)
    public boolean attemptInsert(IdempotencyModel record)
    {
            System.out.println("Coming here to save idempotency"+ record.getIdempotencyKey());
            em.persist(record);
            em.flush();
            //idempotencyRepo.saveAndFlush(record);
            return true;

    }

    @Transactional(propagation = REQUIRES_NEW)
    public IdempotencyModel fetchAndRepairIfStale(String key)
    {
        return idempotencyRepo.findById(key).map( existingRecord ->{
            if(isStale(existingRecord))
            {
                existingRecord.setStatus("FAILED");
                idempotencyRepo.save(existingRecord);
                return existingRecord;
            }
            return existingRecord;
        }).orElse(null);

    }

    public boolean isStale(IdempotencyModel record)
    {
        return record.getStatus().equalsIgnoreCase("PROCESSING")
                && record.getLockedAt() != null
                && record.getLockedAt().isBefore(LocalDateTime.now().minusMinutes(2));
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void markAsFailed (String key)
    {
        IdempotencyModel existing = idempotencyRepo.findById(key).orElseThrow();
        existing.setStatus("FAILED");
        idempotencyRepo.save(existing);

    }


    @Transactional(propagation = REQUIRES_NEW)
    public void markAsFailed (String key,Exception ex)
    {
        IdempotencyModel existing = idempotencyRepo.findById(key).orElseThrow();
        existing.setStatus("FAILED");
        existing.setComments(String.valueOf(ex));
        idempotencyRepo.save(existing);

    }

    @Transactional(propagation = REQUIRES_NEW)
    public void markAsCompleted(String key, Long paymentId)
    {
        IdempotencyModel existing = idempotencyRepo.findById(key).orElseThrow();
        existing.setStatus("COMPLETED");
        existing.setPaymentId(paymentId);
        idempotencyRepo.save(existing);
    }
    @Transactional(propagation = REQUIRES_NEW)
    public void markForRetry(String key)
    {
        IdempotencyModel existing = idempotencyRepo.findById(key).orElseThrow();
        existing.setStatus("RETRY");
        idempotencyRepo.save(existing);
    }
}
