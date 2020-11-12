from django.db import models
from .Animal import Animal

EventEnum = (('r', 'request'), ('p', 'pending'), ('d', 'done'))


class Event(models.Model):
    animal_id = models.ForeignKey(Animal, on_delete=models.CASCADE)
    start_time = models.TimeField()
    end_time = models.TimeField()
    price = models.IntegerField()
    description = models.TextField()
    status = models.CharField(max_length=1, choices=EventEnum)
