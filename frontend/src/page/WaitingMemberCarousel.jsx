import * as React from "react"
import Autoplay from "embla-carousel-autoplay"

import { Card, CardContent } from "../components/ui/card"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "../components/ui/carousel"
import MemberCard from "./MemberCard"

export function CarouselPlugin({waitingList}) {
  const plugin = React.useRef(
    Autoplay({ delay: 2000, stopOnInteraction: true })
  )

  return (
    <div className="flex justify-center items-center">
    <Carousel
      plugins={[plugin.current]}
      className="w-full max-w-xs "
      onMouseEnter={plugin.current.stop}
      onMouseLeave={plugin.current.reset}
    >
      <CarouselContent>
        {waitingList.map((member, index) => (
          <CarouselItem key={index}>
            <div className="p-1 " >
              <Card   >
                <CardContent className=" flex aspect-square items-center justify-center p-6">
                  {/* <span className="text-4xl font-semibold">{index + 1}</span> */}
                  <MemberCard index={index} member={member} />
                </CardContent>
              </Card>
            </div>
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselPrevious />
      <CarouselNext />
    </Carousel>
    </div>
  )
}
