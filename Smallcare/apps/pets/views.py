from django.shortcuts import render


def index(request):
    return render(request, 'pets/index.html')


def test(request):
    return render(request, 'pets/test.html')
