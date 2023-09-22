package guru.qa.rococo.controller;


import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/painting")
public class PaintingController {

  private final PaintingService paintingService;

  @Autowired
  public PaintingController(PaintingService paintingService) {
    this.paintingService = paintingService;
  }

  @GetMapping("/")
  public Page<PaintingJson> getAll(@PageableDefault Pageable pageable) {
    return paintingService.getAll(pageable);
  }

  @GetMapping("/{id}")
  public PaintingJson findPaintingById(@PathVariable("id") String id) {
    return paintingService.findArtistById(id);
  }

  @PatchMapping("/")
  public PaintingJson updatePainting(@RequestBody PaintingJson artist) {
    return paintingService.update(artist);
  }

  @PostMapping("/")
  public PaintingJson addPainting(@RequestBody PaintingJson artist) {
    return paintingService.add(artist);
  }
}
