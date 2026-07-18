package com.belen.CounsellorPortal.serviceImpl;

import com.belen.CounsellorPortal.entity.Counselor;
import com.belen.CounsellorPortal.repo.CounselorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UdeatisServiceImpl implements UserDetailsService {

    private final CounselorRepo counselorRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Counselor counselor = counselorRepo.findCounselorByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Counselor Not Found By That Mail"));

        return new UserPrinciple(counselor);
    }
}
