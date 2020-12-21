package projetal2020

trait Lens[S, P] {
  self =>

  def get: S => P
  def set: (S, P) => S
  def update(p: P)(s: S): S = set(s, p)

  def and[_P](next: Lens[P, _P]): Lens[S, _P] = new Lens[S, _P] {
    override def get: (S) => _P = s => next.get(self.get(s))

    override def set: (S, _P) => S = {
      case (s, _p) => self.set(s, next.set(self.get(s), _p))
    }
  }
}
