package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.PaintingJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

@Service
public class PaintingService {
  private final PaintingRepository paintingRepository;
  private final MuseumRepository museumRepository;
  private final ArtistRepository artistRepository;

  @Autowired
  public PaintingService(PaintingRepository paintingRepository,
                         MuseumRepository museumRepository,
                         ArtistRepository artistRepository) {
    this.paintingRepository = paintingRepository;
    this.museumRepository = museumRepository;
    this.artistRepository = artistRepository;
  }

  @Transactional(readOnly = true)
  public @Nonnull Page<PaintingJson> getAll(@Nonnull Pageable pageable) {
    return paintingRepository.findAll(pageable).map(PaintingJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull PaintingJson findArtistById(@Nonnull String id) {
    return PaintingJson.fromEntity(
        paintingRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(NotFoundException::new)
    );
  }

  @Transactional
  public @Nonnull PaintingJson update(@Nonnull PaintingJson painting) {
    PaintingEntity paintingEntity = getRequiredPainting(painting.id());
    paintingEntity.setTitle(painting.title());
    paintingEntity.setContent(painting.content());
    if (painting.museum() != null) {
      if (paintingEntity.getMuseum() == null
          || !Objects.equals(paintingEntity.getMuseum().getId(), painting.museum().id())) {
        MuseumEntity museumEntity = museumRepository.findById(painting.museum().id()).orElseThrow(
            NotFoundException::new
        );
        museumEntity.addPaintings(paintingEntity);
      }
    }

    if (painting.artist() != null) {
      if (paintingEntity.getArtist() == null
          || !Objects.equals(paintingEntity.getArtist().getId(), painting.artist().id())) {
        ArtistEntity artistEntity = artistRepository.findById(painting.artist().id()).orElseThrow(
            NotFoundException::new
        );
        artistEntity.addPaintings(paintingEntity);
      }
    }
    return PaintingJson.fromEntity(
        paintingRepository.save(paintingEntity)
    );
  }

  @Transactional
  public PaintingJson add(PaintingJson museum) {
    return PaintingJson.fromEntity(
        paintingRepository.save(
            museum.toEntity()
        )
    );
  }

  private @Nonnull PaintingEntity getRequiredPainting(@Nonnull UUID id) {
    return paintingRepository.findById(id).orElseThrow(
        NotFoundException::new
    );
  }
}
