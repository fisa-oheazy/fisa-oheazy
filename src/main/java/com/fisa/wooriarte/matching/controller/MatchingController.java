package com.fisa.wooriarte.matching.controller;

import com.fisa.wooriarte.matching.domain.MatchingStatus;
import com.fisa.wooriarte.matching.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchingController {

    private final MatchingService matchingService;

    @Autowired
    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/admin/matchings")
    public String getAllMatching() {
        return matchingService.getAllMatching().toString();
    }

    @GetMapping("/admin/matchings/{matching-id}")
    public String getMatching(@PathVariable(value = "matching-id") Long matchingId) {
        return matchingService.getMatching(matchingId).toString();
    }

    // 매칭 수정 (매칭에서는 상태만 수정가능)
    @PatchMapping("/admin/matchings/{matching-id}")
    public String updateMatching(@PathVariable(value = "matching-id") Long matchingId, @RequestBody MatchingStatus matchingStatus) {
        if(matchingService.updateMatching(matchingId, matchingStatus))
            return "success";
        return "fail";
    }

    // 공간 대여자 -> 프로젝트 매니저 매칭 신청
    @RequestMapping("/space/{space-id}/request")
    public String addMatchingBySpaceRental(@PathVariable(value = "space-id") Long spaceId, @RequestBody Long projectId) {
        return matchingService.addMatchingBySpaceRental(spaceId, projectId).toString();
    }

    // 프로젝트 매니저 -> 공간 대여자 매칭 신청
    @RequestMapping("/project/{project-id}/request")
    public String addMatchingByProjectManager(@PathVariable(value = "project-id") Long projectId, @RequestBody Long spaceId) {
        return matchingService.addMatchingByProjectManager(projectId, spaceId).toString();
    }

    // 매칭 수락, 거절
    @PostMapping("/matching/{matching-id}")
    public String approvalMatching(@PathVariable(value = "matching-id") Long id, @RequestBody boolean accept) {
        if(matchingService.updateMatching(id, accept ? MatchingStatus.WAITING : MatchingStatus.CANCEL)) {
            return "success";
        }
        return "fail";
    }

    // 공간 대여자 대기중인 매칭 조회
    @GetMapping("/space-rental/{space-rental-id}/waitings")
    public String findSpaceRentalWaitingMatching(@PathVariable(value = "space-rental-id") Long spaceRentalId) {
        return matchingService.findSpaceRentalWaitingMatching(spaceRentalId).toString();
    }

    // 공간 대여자 받은 매칭 조회
    @GetMapping("/space-rental/{space-rental-id}/offers")
    public String findSpaceRentalOfferMatching(@PathVariable(value = "space-rental-id") Long spaceRentalId) {
        return matchingService.findSpaceRentalOfferMatching(spaceRentalId).toString();
    }

    // 공간 대여자 상사된 매칭 조회
    @GetMapping("/space-rental/{space-rental-id}/success")
    public String findSpaceRentalSuccessMatching(@PathVariable(value = "space-rental-id") Long spaceRentalId) {
        return matchingService.findSpaceRentalSuccessMatching(spaceRentalId).toString();
    }

    // 프로젝트 매니저 대기중인 매칭 조회
    @GetMapping("/project-managers/{project-manager-id}/waitings")
    public String findProjectManagerWaitingMatching(@PathVariable(value = "project-manager-id") Long projectManagerId) {
        return matchingService.findProjectManagerWaitingMatching(projectManagerId).toString();
    }

    // 프로젝트 매니저 받은 매칭 조회
    @GetMapping("/project-managers/{project-manager-id}/offers")
    public String findProjectManagerOfferMatching(@PathVariable(value = "project-manager-id") Long projectManagerId) {
        return matchingService.findProjectManagerOfferMatching(projectManagerId).toString();
    }

    // 프로젝트 매니저 성사된 매칭 조회
    @GetMapping("/project-managers/{project-manager-id}/success")
    public String findProjectManagerSuccessMatching(@PathVariable(value = "project-manager-id") Long projectManagerId) {
        return matchingService.findProjectManagerSuccessMatching(projectManagerId).toString();
    }
}