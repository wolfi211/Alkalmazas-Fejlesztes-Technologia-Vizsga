package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private SponsorRepository sponsorRepository;

    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository, SponsorRepository sponsorRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @GetMapping("/tallestrunner")
    public String getTallestRunnerName() {
        List<RunnerEntity> runners = runnerRepository.findAll();
        if(runners.size() > 0) {
            RunnerEntity tallest = runners.get(0);
            for(RunnerEntity runner : runners) {
                if(runner.getHeight() > tallest.getHeight()){
                    tallest = runner;
                }
            }
            return tallest.getRunnerName();
        }
        return "";
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    @PostMapping("/{id}/setsponsor")
    public ResponseEntity setSponsor(@PathVariable Long id, @RequestBody SponsorRequest sponsorRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        SponsorEntity sponsor = sponsorRepository.findById(sponsorRequest.getId()).orElse(null);
        if(runner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
        if(sponsor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sponsor with ID " + sponsorRequest.getId() + " not found");
        }
        runner.setSponsor(sponsor);
        runnerRepository.save(runner);
        return ResponseEntity.ok().build();
    }

    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    public static class SponsorRequest {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
