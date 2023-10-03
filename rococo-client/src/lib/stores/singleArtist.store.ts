import {writable} from "svelte/store";
import type {PaintingType} from "$lib/types/Painting";

export const singleArtistStore = writable<{
    paintings: PaintingType[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string [],
}>({paintings: [], noMoreData: false, isLoading: false, ignoreIds: []});